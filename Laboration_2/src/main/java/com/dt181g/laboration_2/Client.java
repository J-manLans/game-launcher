package com.dt181g.laboration_2;

import java.util.Random;

/**
 * Represents a consumer or producer in the resource pool system.
 * This abstract class implements the {@link Runnable} interface and is responsible
 * for the concrete clients consuming or producing of resources from the shared {@link ResourcePool}.
 * <p>
 * The class generates a random amount of resources within a specified range based on the maxAdd variable,
 * and either adds or subtracts it from the pool based on the operation variable,
 * both of these are provided by the concrete client.
 * Then it periodically sleeps for a random duration between modification cycles.
 * </p>
 * <p>
 * Upon interruption, the thread will sleep until it is interrupted again, ensuring
 * that it does not consume CPU resources unnecessarily when inactive.
 * </p>
 *
 * @author Joel Lansgren
 */
abstract class Client implements Runnable {
    private final int maxAdd;
    private final int operation;

    Client(final int maxAdd, final int operation) {
        this.maxAdd = maxAdd;
        this.operation = operation;
    }

    /**
     * The main execution method for the clients.
     * <p>
     * This method continuously consumes or produces resources from the {@link ResourcePool}
     * based on the operation provided from the client, and sleeps for a random duration
     * between each modification cycle. If interrupted (by the {@code Manager}), it will wait
     * until it is interrupted again before resuming modification.
     * </p>
     */
    @Override
    public void run() {
        final Random randomizer = new Random();
        int resource;

        while (true) {
            resource = randomizer.nextInt(maxAdd) + AppConfig.CLIENT_MIN_ADD;
            ResourcePool.INSTANCE.modifyResources(operation * resource);

            try {
                Thread.sleep(randomizer.nextLong(AppConfig.CLIENTS_MAX_SLEEP) + AppConfig.CLIENTS_MIN_SLEEP);
            } catch (final InterruptedException e) {
                // Enters here when interrupt() is called and the thread is
                // switched from the active list and added to the wait list
                try {  // Put the thread to sleep for a very long time
                    Thread.sleep(Long.MAX_VALUE);
                } catch (final InterruptedException e1) {
                    // Here we end up when interrupt() is called on this thread the next time,
                    // which is when it is switched from the waiting list to the active list.
                    // And after this it continues with its main execution in the while loop
                }
            }
        }
    }
}
