package com.dt181g.laboration_2;

import java.util.Random;

public class Producer implements Runnable {
    private final ResourcePool resourcePool;
    private static final int minValue = 1;
    private static final int maxResourceValue = 10;
    private static final int minSleepValue = 1000;
    private static final int maxSleepValue = 5000;


    Producer(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void run() {
        Random randomizer = new Random();
        while (true) {
            resourcePool.modifyResources((randomizer.nextInt(maxResourceValue) + minValue));

            try {
                Thread.sleep(randomizer.nextInt(maxSleepValue) + minSleepValue);
            } catch (InterruptedException e) {
                System.out.println("Producer will terminate");
                break;
            }
        }
    }
}
