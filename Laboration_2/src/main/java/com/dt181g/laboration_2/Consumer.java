package com.dt181g.laboration_2;

import java.util.Random;

public class Consumer implements Runnable {
    private final ResourcePool resourcePool;
    private static final int minValue = 1;
    private static final int maxResourceValue = 20;
    private static final int maxSleepValue = 5000;

    Consumer(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void run() {
        final Random randomizer = new Random();

        while (true) {
            resourcePool.modifyResources(-(randomizer.nextInt(maxResourceValue) + minValue));
            try {
                Thread.sleep(randomizer.nextInt(maxSleepValue) + minValue);
            } catch (InterruptedException e) {
                System.out.println("Consumer will terminate");
                break;
            }
        }
    }
}
