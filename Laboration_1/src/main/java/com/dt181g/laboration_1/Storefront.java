package com.dt181g.laboration_1;

import java.util.ArrayList;
import java.util.Random;


/**
 * The Storefront class is start point for the application. It simulates a pool of clients that
 * execute tasks using worker threads from the {@code ThreadManager}.
 * <p>
 * It creates a random number of clients (between 110 and 120), let them interact with worker threads,
 * and waits for each client to complete before shutting down the thread manager.
 * </p>
 * @author Joel Lansgren
 */
public class Storefront {
    final Random randomizer = new Random();
    final int clientPool = randomizer.nextInt(11) + 100;
    final ArrayList<Client> clients = new ArrayList<Client>(20);
    final ThreadManager manager = ThreadManager.INSTANCE;

    Storefront() {
        System.out.println("\nClients: " + clientPool + "\n");
        welcomeClients();
        waitForClientsToLeave();
        endOfDay();
    }

    /**
     * Creates the list of clients and starts them
     */
    private void welcomeClients(){
        for (int i = 1; i <= clientPool; i++) {
            clients.add(new Client("Client " + i));
            clients.getLast().start();
        }
    }

    /**
     * Joins the client threads and wait for them to terminate
     */
    private void waitForClientsToLeave() {
        for (Client client : clients) {
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        clients.clear();
    }

    /**
     * Print some statistics to the console and utilizes the manager enum to shut down the thread pool.
     */
    private void endOfDay() {

        System.out.println(
            String.format(
                "\nWe had %d clients today, %d of them obtained a multilayered random number from the threads in the Thread Pool, then went on their way.\n",
                clientPool,
                manager.getThreadUtilizations()
            )
        );

        manager.shutdown();
        System.out.println("\nThe Thread Pool is empty. Good work guys, see yah tomorrow!\n");
    }
}
