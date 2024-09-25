package com.dt181g.laboration_2;

import java.util.concurrent.atomic.AtomicInteger;

public enum ResourcePool {
    INSTANCE;
    private final AtomicInteger resources = new AtomicInteger(50);

    public void modifyResources(int resource) {
            this.resources.addAndGet(resource);
    }

    public int getResources() {
        return this.resources.get();
    }

    public void drawPool() {

    }
}
