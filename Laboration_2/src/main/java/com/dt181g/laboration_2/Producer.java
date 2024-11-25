package com.dt181g.laboration_2;

import java.util.Random;

/**
 * Represents a producer in the resource pool system.
 * This class implements the {@link Runnable} interface and is responsible
 * for producing resources and adding them to the shared {@link ResourcePool}.
 * <p>
 * The producer generates a random amount of resources within a specified range,
 * and periodically sleeps for a random duration between resource productions.
 * </p>
 * <p>
 * Upon interruption, the producer will wait until it is interrupted again, ensuring
 * that it does not consume CPU resources unnecessarily when inactive.
 * </p>
 *
 * @author Joel Lansgren
 */
class Producer implements Runnable {
    /**
     * The main execution method for the producer.
     * <p>
     * This method continuously generates resources and adds them to the {@link ResourcePool}.
     * It sleeps for a random duration between each production cycle. If interrupted (by the {@code Manager}),
     * it will wait until it is interrupted again before resuming production.
     * </p>
     */
    @Override
    public void run() {
        final Random randomizer = new Random();
        int resource;

        while (true) {
            resource = randomizer.nextInt(AppConfig.CONSUMER_MAX_ADD) + AppConfig.CLIENT_MIN_ADD;
            ResourcePool.INSTANCE.modifyResources(resource);

            try {
                Thread.sleep(randomizer.nextLong(AppConfig.CLIENTS_MAX_SLEEP) + AppConfig.CLIENTS_MIN_SLEEP);
            } catch (final InterruptedException e) {
                // Enters here when interrupt() is called and the thread is
                // switched from the active list and added to the wait list
                try {  // Put the thread to sleep for a very long time
                    Thread.sleep(Long.MAX_VALUE);
                } catch (final InterruptedException e1) {
                    // Here we end ut when interrupt() is called on this thread the next time
                    // which is when it is switched from the waiting list to the active list.
                    // And after this it continues with its main execution in the while loop
                }
            }
        }
    }
}
