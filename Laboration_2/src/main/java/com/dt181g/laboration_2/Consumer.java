package com.dt181g.laboration_2;

import java.util.Random;

public class Consumer implements Runnable {
    private final ResourcePool resourcePool;
    private static final int minValue = 1;
    private static final int maxResourceValue = 20;
    private static final int minSleepValue = 1000;
    private static final int maxSleepValue = 2000;

    Consumer(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void run() {
        final Random randomizer = new Random();

        while (true) {
            int resource = randomizer.nextInt(maxResourceValue) + minValue;
            resourcePool.modifyResources(-resource);

            try {
                Thread.sleep(randomizer.nextInt(maxSleepValue) + minSleepValue);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
