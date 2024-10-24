package com.dt181g.laboration_2;

import java.util.ArrayDeque;

/**
 * The Manager class represents the main GUI component for managing producers and consumers
 * in a resource pool system. This class extends JFrame and is responsible for
 * creating and managing the user interface, as well as controlling the lifecycle of
 * producer and consumer threads.
 * <p>
 * The Manager maintains the current state of the resource pool and updates the GUI
 * to reflect changes in resource availability. It employs separate panels for producers,
 * consumers, and a central display for resources, facilitating real-time visualization of
 * the resource pool's state.
 * </p>
 *
 * @author Joel Lansgren
 */
enum Manager {
    INSTANCE;

    // Thread lists
    private final ArrayDeque<Thread> activeProducers = new ArrayDeque<>();
    private final ArrayDeque<Thread> activeConsumers = new ArrayDeque<>();
    private final ArrayDeque<Thread> waitingProducers = new ArrayDeque<>();
    private final ArrayDeque<Thread> waitingConsumers = new ArrayDeque<>();

    private final ResourcePool resourcePool = ResourcePool.INSTANCE;
    private final int startingPoolSize = resourcePool.pollForResource();
    private int producers = AppConfig.STARTING_PRODUCERS;  // These are the initial number of clients for the setup
    private int consumers = AppConfig.STARTING_CONSUMERS;  // They will be modified continuously to update the GUI
    private final int largerQuantity = Math.max(producers, consumers);
    private int tempPoolSize = startingPoolSize;
    private int currentPoolSize;
    private final Runnable producer = new Producer();
    private final Runnable consumer = new Consumer();

     /**
     * Starts all threads that are managing the producers and consumers.
     * Each thread is initiated to begin its execution, allowing producers
     * and consumers to operate concurrently.
     */
    void initiateAndStartThreads() {
        for (int i = 1; i <= largerQuantity; i++) {
            if (i <= producers) {
                this.activeProducers.add(new Thread(producer, AppConfig.PRODUCER));
                this.activeProducers.peekLast().start();
            }

            if (i <= consumers) {
                this.activeConsumers.add(new Thread(consumer, AppConfig.CONSUMER));
                this.activeConsumers.peekLast().start();
            }
        }
    }

    /**
     * This method needs to be executed on the EDT since it refreshes the GUI to reflect the current state
     * of the resource pool. It checks if the resource pool size has changed,
     * and if so, updates the clients and redraws the GUI components accordingly.
     */
    void refreshGUI(final ResourceFrame resourceFrame) {
        this.currentPoolSize = resourcePool.pollForResource();
        if (this.currentPoolSize != this.tempPoolSize) {
            this.modifyClients();
            this.updateClientCount();
            this.reDrawGUI(resourceFrame);
        }
        this.tempPoolSize = this.currentPoolSize;
    }

    /**
     * Modifies the clients based on the current pool size. If the resource pool size
     * is below 50, it may remove a consumer and add a producer;
     * if it is 200 or above, it may remove a producer and add a new consumer.
     */
    private void modifyClients() {
        switch (this.currentPoolSize / AppConfig.STARTING_RESOURCES) {
            case AppConfig.THRESHOLD_LOW -> this.modifyClientLists(
                this.activeConsumers,
                this.waitingConsumers,
                this.activeProducers,
                this.waitingProducers,
                this.producer,
                AppConfig.PRODUCER
            );
            case AppConfig.THRESHOLD_MAX -> this.modifyClientLists(
                this.activeProducers,
                this.waitingProducers,
                this.activeConsumers,
                this.waitingConsumers,
                this.consumer,
                AppConfig.CONSUMER
            );
            default -> { } // No action needed for this value since it is inside the interval.
        }
    }

    /**
     * Modifies the client thread lists by removing and adding threads based on the current
     * resource availability. It interrupts a specified active client thread,
     * places it into the waiting list, and either starts a waiting client thread
     * if available or creates a new one. This approach allows for dynamic management
     * of active client threads, ensuring that the maximum limit is respected.
     *
     * @param removeActiveList the list of active clients where a thread is to be removed
     * @param removeWaitingList the list where interrupted clients are placed
     * @param addActiveList the list of active clients where a thread is to be added
     * @param removeWaitingList the list from which clients are drawn when adding new ones if it is not empty
     * @param newClient the Runnable instance of the client to be added (producer or consumer)
     * @param clientName the name of the client thread to be created
     */
    private void modifyClientLists(
        final ArrayDeque<Thread> removeActiveList,
        final ArrayDeque<Thread> addWaitingList,
        final ArrayDeque<Thread> addActiveList,
        final ArrayDeque<Thread> removeWaitingList,
        final Runnable newClient,
        final String clientName
    ) {
        boolean switchedClient = false;

        if (removeActiveList.size() > 0) {
            // Make the client enter a wait state
            removeActiveList.peekFirst().interrupt();
            // Moves client from its active to its waiting list
            addWaitingList.add(removeActiveList.pollFirst());
        }

        if (addActiveList.size() < AppConfig.MAX_ACTIVE_CLIENTS) {
            if (removeWaitingList.size() > 0) {
                // Moves opposite client from its waiting to its active list
                addActiveList.add(removeWaitingList.pollFirst());
                // Restarts client
                addActiveList.peekLast().interrupt();
                switchedClient = true;
            }

            // Adds new client thread and starts it up if no clients have
            // been switched from the waiting list to the client list
            if (!switchedClient) {
                addActiveList.add(new Thread(newClient, clientName));
                addActiveList.peekLast().start();
            }
        }
    }

    /**
     * Updates the count of active producers and consumers based on the current
     * name of the client threads. This method iterates through all client threads
     * and increments the respective counts.
     */
    private void updateClientCount() {
        this.producers = this.activeProducers.size();
        this.consumers = this.activeConsumers.size();
    }

    /**
     * Redraws the graphical user interface (GUI) components to display the current
     * counts of producers, consumers, and the current resource pool size, as well as
     * the circle representing the size of the resources.
     */
    private void reDrawGUI(final ResourceFrame resourceFrame) {
        resourceFrame.refreshGUI(this.producers, this.currentPoolSize, this.consumers);
    }
}
