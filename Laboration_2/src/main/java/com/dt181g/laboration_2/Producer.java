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
    private final ResourcePool resourcePool = ResourcePool.INSTANCE;

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

        while (true) {
            final int resource = randomizer.nextInt(AppConfig.CONSUMER_MAX_ADD) + AppConfig.CLIENT_MIN_ADD;
            this.resourcePool.modifyResources(resource);

            try {
                Thread.sleep(randomizer.nextLong(AppConfig.CLIENTS_MAX_SLEEP) + AppConfig.CLIENTS_MIN_SLEEP);
            } catch (final InterruptedException e) {
                synchronized (Thread.currentThread()) {  // Enters here when interrupt() is called
                    while (true) {  // Protects from spurious wakeup
                        try {
                            Thread.currentThread().wait();
                        } catch (final InterruptedException e1) {
                            break;  // Breaks out when interrupt() is called
                        }
                    }
                }
            }
        }
    }
}
