package com.dt181g.laboration_1;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * The {@code Lab1} class is the entry point of the application. It simulates multiple clients that utilize
 * worker threads from a thread pool managed by the {@code ThreadManager}. Each client runs its own thread,
 * waits for the worker thread to complete a task, and then returns the thread back to the pool.
 *
 * This class cannot be instantiated as it is designed to contain only static methods.
 * @author Joel Lansgren
 */
public final class Lab1 {
    private Lab1() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method is the entry point for the application. It simulates a pool of clients that
     * execute tasks using worker threads from the {@code ThreadManager}.
     * <p>
     * It creates a random number of clients (between 10 and 20), let them interact with worker threads,
     * and waits for each client to complete before shutting down the thread manager.
     * </p>
     *
     * @param args command-line arguments (not used in this application).
     */
    public static void main(final String... args) {
        final Random randomizer = new Random();
        final int clientPool = randomizer.nextInt(11) + 10;
        final Deque<Client> clients = new LinkedList<Client>();
        final ThreadManager manager = ThreadManager.INSTANCE;

        System.out.println("\nClients: " + clientPool + "\n");

        for (int i = 1; i <= clientPool; i++) {
            clients.add(new Client("Client " + i));
        }

        for (Client client : clients) {
            client.start();
        }

        for (Client client : clients) {
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(
            String.format(
                "\nWe had %d clients today, %d of them executed threads in the Thread pool and then went on their way.\n",
                clientPool,
                manager.getThreadUtilizations()
            )
        );

        manager.shutdown();
    }
}
