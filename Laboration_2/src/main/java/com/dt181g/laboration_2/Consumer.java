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
public class Consumer implements Runnable {
    private final ResourcePool resourcePool;

     /**
     * Constructs a Consumer with the specified resource pool.
     *
     * @param resourcePool the {@link ResourcePool} singleton to which this consumer will add resources.
     */
    Consumer(final ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

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

        while (true) {
            int resource = randomizer.nextInt(AppConfig.CONSUMER_MAX_ADD) + AppConfig.CLIENT_MIN_ADD;
            resourcePool.modifyResources(-resource);

            try {
                Thread.sleep(randomizer.nextLong(AppConfig.CLIENTS_MAX_SLEEP) + AppConfig.CLIENTS_MIN_SLEEP);
            } catch (InterruptedException e) {
                synchronized (Thread.currentThread()) {
                    while (true) {  // Protects from spurious wakeup
                        try {
                            Thread.currentThread().wait();
                        } catch (InterruptedException e1) {
                            break;  // Breaks out when interrupt() is called
                        }
                    }
                }
            }
        }
    }
}
