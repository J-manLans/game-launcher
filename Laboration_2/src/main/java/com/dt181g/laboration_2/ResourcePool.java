package com.dt181g.laboration_2;

/**
 * Singleton enum that represents a simplified resource pool in the system.
 * This class is responsible for managing a pool of resources
 * and provides synchronized methods to modify and access the resources.
 * <p>
 * The resource pool starts with a predefined amount of resources and ensures
 * that any modifications keep the resources within specified minimum and maximum bounds.
 * </p>
 */
public enum ResourcePool {
    INSTANCE;
    private int resources = 50;
    private int newResources;
    private final int minResources = 0;
    private final int maxResources = 250;

    /**
     * Modifies the current amount of resources in the pool.
     * <p>
     * This method adds a specified amount of resources to the current total,
     * ensuring that the new total remains within the defined bounds.
     * This method is synchronized to prevent concurrent modification issues.
     * </p>
     *
     * @param resourceToAdd the amount of resources to add (can be negative to remove resources)
     */
    public synchronized void modifyResources(final int resourceToAdd) {
        this.newResources = this.resources + resourceToAdd;

        if (this.newResources >= this.minResources
            && this.newResources <= this.maxResources) {
            this.resources = this.newResources;
        }
    }

    /**
     * Retrieves the current amount of resources in the pool.
     *
     * @return the current amount of resources available in the pool.
     */
    public int pollForResource() {
        return this.resources;
    }
}
