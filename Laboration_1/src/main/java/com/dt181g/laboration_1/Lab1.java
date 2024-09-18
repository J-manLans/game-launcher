package com.dt181g.laboration_1;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * The main starting point for laboration 1.
 * @author Joel Lansgren
 */
public final class Lab1 {
    private Lab1() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * This method simulate clients by using lambda expressions
     * to create threads that need different tasks done
     * @param args command arguments.
     */
    public static void main(final String... args) {
        final Random randomizer = new Random();
        final int CLIENT_POOL = randomizer.nextInt(11) + 10;
        final Deque<Client> clients = new LinkedList<Client>();
        final ThreadManager manager = ThreadManager.INSTANCE;

        System.out.println("\nClients: " + CLIENT_POOL + "\n");

        for (int i = 1; i <= CLIENT_POOL; i++) {
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
        } System.out.println("\nWe had " + CLIENT_POOL + " clients today, " + manager.getThreadUtilizations() + " of them executed threads in the Thread pool and then went on their way.\n");

        manager.shutdown();
    }
}
