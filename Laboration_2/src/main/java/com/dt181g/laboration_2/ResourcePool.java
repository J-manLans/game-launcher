package com.dt181g.laboration_2;

import java.util.concurrent.atomic.AtomicInteger;

public enum ResourcePool {
    INSTANCE;
    private final AtomicInteger resources = new AtomicInteger(50);
    private final int minResources = 0;
    private final int maxResources = 250;
    private int currentPoolSize;

    public synchronized void modifyResources(int resourceToAdd) {
        this.currentPoolSize = this.resources.get();
        if (!(this.currentPoolSize + resourceToAdd < minResources) &&
            !(this.currentPoolSize + resourceToAdd > maxResources)) {
            this.resources.addAndGet(resourceToAdd);
        }
    }

    public int getResources() {
        return this.resources.get();
    }
}
