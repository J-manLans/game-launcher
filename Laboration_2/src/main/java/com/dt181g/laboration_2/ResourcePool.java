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
enum ResourcePool {
    INSTANCE;
    private int resources = AppConfig.STARTING_RESOURCES;
    private int newResources;

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
    synchronized void modifyResources(final int resourceToAdd) {
        this.newResources = this.resources + resourceToAdd;

        if (this.newResources >= AppConfig.MIN_RESOURCE_BOUND
            && this.newResources <= AppConfig.MAX_RESOURCE_BOUND) {
            this.resources = this.newResources;
        }
    }

    /**
     * Retrieves the current amount of resources in the pool.
     *
     * @return the current amount of resources available in the pool.
     */
    int pollForResource() {
        return this.resources;
    }
}
