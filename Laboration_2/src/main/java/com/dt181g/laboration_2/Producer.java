package com.dt181g.laboration_2;

import java.util.Random;

public class Producer implements Runnable {
    private final ResourcePool resourcePool;
    private boolean shutdown = false;
    private final int minValue = 1;
    private final int maxResourceValue = 10;
    private final int maxSleepValue = 5000;

    Producer(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void run() {
        Random randomizer = new Random();
        while (true) {
            resourcePool.modifyResources((randomizer.nextInt(maxResourceValue) + minValue));
            try {
                Thread.sleep(randomizer.nextInt(maxSleepValue) + minValue);
            } catch (InterruptedException e) {
                System.out.println("Producer will terminate");
                break;
            }
        }
    }
}
