package com.dt181g.laboration_2;

import java.util.Random;

/**
 * Represents a consumer in the resource pool system.
 * This class implements the {@link Runnable} interface and is responsible
 * for consuming resources from the shared {@link ResourcePool}.
 * <p>
 * The consumer generates a random amount of resources within a specified range,
 * and periodically sleeps for a random duration between consumption cycles.
 * </p>
 * <p>
 * Upon interruption, the consumer will wait until it is interrupted again, ensuring
 * that it does not consume CPU resources unnecessarily when inactive.
 * </p>
 *
 * @author Joel Lansgren
 */
class Consumer implements Runnable {
    /**
     * The main execution method for the consumer.
     * <p>
     * This method continuously consumes resources from the {@link ResourcePool} and
     * sleeps for a random duration between each consumption cycle. If interrupted (by the {@code Manager}),
     * it will wait until it is interrupted again before resuming consumption.
     * </p>
     */
    @Override
    public void run() {
        final Random randomizer = new Random();
        int resource;

        while (true) {
            resource = randomizer.nextInt(AppConfig.CONSUMER_MAX_ADD) + AppConfig.CLIENT_MIN_ADD;
            ResourcePool.INSTANCE.modifyResources(-resource);

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
