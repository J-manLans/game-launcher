package com.dt181g.laboration_2;


public enum ResourcePool {
    INSTANCE;
    private int resources = 50;
    private int newResources;
    private final int minResources = 0;
    private final int maxResources = 250;

    public synchronized void modifyResources(int resourceToAdd) {
        this.newResources = this.resources + resourceToAdd;

        if (this.newResources >= this.minResources &&
            this.newResources <= this.maxResources) {
            this.resources = this.newResources;
        }
    }

    public int getResources() {
        return this.resources;
    }
}
