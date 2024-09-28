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
public class Producer implements Runnable {
    private final ResourcePool resourcePool;
    private final int minValue = 1;
    private final int maxResourceValue = 10;
    private final int minSleepValue = 1000;
    private final int maxSleepValue = 5000;

    /**
     * Constructs a Producer with the specified resource pool.
     *
     * @param resourcePool the {@link ResourcePool} singleton to which this producer will add resources.
     */
    Producer(final ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

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
        Random randomizer = new Random();

        while (true) {
            int resource = randomizer.nextInt(this.maxResourceValue) + this.minValue;
            resourcePool.modifyResources(resource);

            try {
                Thread.sleep(randomizer.nextInt(this.maxSleepValue) + this.minSleepValue);
            } catch (InterruptedException e) {
                synchronized (Thread.currentThread()) {  // Enters here when interrupt() is called
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
