package com.dt181g.laboration_2;

import java.util.ArrayDeque;

/**
 * The Manager enum is a singleton that manages the lifecycle and states of producer and consumer threads
 * within a resource pool system. It dynamically adjusts the number of active producers and consumers
 * based on the current resource pool size to maintain system balance and performance.
 *
 * @author Joel Lansgren
 */
enum Manager {
    INSTANCE;

    // Thread lists
    private final ArrayDeque<Thread> activeProducers = new ArrayDeque<>();
    private final ArrayDeque<Thread> activeConsumers = new ArrayDeque<>();
    private final ArrayDeque<Thread> sleepingProducers = new ArrayDeque<>();
    private final ArrayDeque<Thread> sleepingConsumers = new ArrayDeque<>();

    private int producers = AppConfig.STARTING_PRODUCERS;  // These are the initial number of clients for the setup
    private int consumers = AppConfig.STARTING_CONSUMERS;  // They will be modified continuously to update the GUI
    private final int largerQuantity = Math.max(producers, consumers);
    private int oldPoolSize = AppConfig.STARTING_RESOURCES;
    private int currentPoolSize;
    private final Runnable producer = new Producer();
    private final Runnable consumer = new Consumer();

     /**
     * Starts all threads that are managing the producers and consumers.
     * Each thread is initiated to begin its execution, allowing producers
     * and consumers to operate concurrently.
     */
    void initiateAndStartThreads() {
        for (int i = 1; i <= this.largerQuantity; i++) {
            if (i <= this.producers) {
                this.activeProducers.add(new Thread(this.producer, AppConfig.PRODUCER));
                this.activeProducers.peekLast().start();
            }

            if (i <= this.consumers) {
                this.activeConsumers.add(new Thread(this.consumer, AppConfig.CONSUMER));
                this.activeConsumers.peekLast().start();
            }
        }
    }

    /**
     * Checks if the resource pool size has changed by comparing its current value to the old one.
     * if so, it modifies the clients based on the current pool size. If the resource pool size
     * is below 50, it may remove a consumer and add a producer;
     * if it is 200 or above, it may remove a producer and add a new consumer.
     *
     * @return the updated value of the size of the resource pool.
     */
    int modifyClients() {
        this.currentPoolSize = ResourcePool.INSTANCE.pollForResource();
        if (this.currentPoolSize != this.oldPoolSize) {
            switch (this.currentPoolSize / AppConfig.STARTING_RESOURCES) {
                case AppConfig.THRESHOLD_LOW -> this.modifyClientLists(
                    this.activeConsumers,
                    this.sleepingConsumers,
                    this.activeProducers,
                    this.sleepingProducers,
                    this.producer,
                    AppConfig.PRODUCER
                );
                case AppConfig.THRESHOLD_MAX -> this.modifyClientLists(
                    this.activeProducers,
                    this.sleepingProducers,
                    this.activeConsumers,
                    this.sleepingConsumers,
                    this.consumer,
                    AppConfig.CONSUMER
                );
                default -> { } // No action needed for this value since it is inside the interval.
            }
            this.oldPoolSize = this.currentPoolSize;
        }
        return this.currentPoolSize;
    }

    /**
     * Modifies the client thread lists by removing and adding threads based on the current
     * resource availability. It interrupts a specified active client thread,
     * places it into the sleeping list, and either starts a sleeping client thread
     * if available or creates a new one. This approach allows for dynamic management
     * of active client threads, ensuring that the maximum limit is respected.
     *
     * @param removeActiveList the list of active clients where a thread is to be removed
     * @param removeSleepingList the list where interrupted clients are placed
     * @param addActiveList the list of active clients where a thread is to be added
     * @param removeSleepingList the list from which clients are drawn when adding new ones if it is not empty
     * @param newClient the Runnable instance of the client to be added (producer or consumer)
     * @param clientName the name of the client thread to be created
     */
    private void modifyClientLists(
        final ArrayDeque<Thread> removeActiveList,
        final ArrayDeque<Thread> addSleepingList,
        final ArrayDeque<Thread> addActiveList,
        final ArrayDeque<Thread> removeSleepingList,
        final Runnable newClient,
        final String clientName
    ) {

        if (removeActiveList.size() > 0) {
            // Make the client enter a long sleep state
            removeActiveList.peekFirst().interrupt();
            // Moves client from its active to its waiting list
            addSleepingList.add(removeActiveList.pollFirst());
        }

        if (addActiveList.size() < AppConfig.MAX_ACTIVE_CLIENTS) {
            if (removeSleepingList.size() > 0) {
                // Moves opposite client from its waiting to its active list
                addActiveList.add(removeSleepingList.pollFirst());
                // Restarts client
                addActiveList.peekLast().interrupt();
            } else {
                // Adds new client thread and starts it up if no clients can be
                // switched from the waiting list to the client list
                addActiveList.add(new Thread(newClient, clientName));
                addActiveList.peekLast().start();
            }
        }
    }

    /**
     * Returns the number of active producer threads.
     *
     * @return the current count of active producers
     */
    public int getActiveProducers() {
        return activeProducers.size();
    }

    /**
     * Returns the number of active consumer threads.
     *
     * @return the current count of active consumers
     */
    public int getActiveConsumers() {
        return activeConsumers.size();
    }
}
