package com.dt181g.laboration_2;

import java.util.Random;

public class Consumer implements Runnable {
    private final ResourcePool resourcePool;
    private final int minValue = 1;
    private final int maxResourceValue = 20;
    private final int minSleepValue = 1000;
    private final int maxSleepValue = 5000;

    Consumer(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void run() {
        final Random randomizer = new Random();

        while (true) {
            int resource = randomizer.nextInt(this.maxResourceValue) + this.minValue;
            resourcePool.modifyResources(-resource);

            try {
                Thread.sleep(randomizer.nextInt(this.maxSleepValue) + this.minSleepValue);
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
