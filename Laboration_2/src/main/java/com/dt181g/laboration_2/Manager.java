package com.dt181g.laboration_2;

import java.util.LinkedList;

public enum Manager {
    INSTANCE;
    private final LinkedList<Thread> clients = new LinkedList<>();
    private final int initialProducers = 6;
    private final int initialConsumers = 5;
    private final int largerQuantity = Math.max(initialProducers, initialConsumers);
    private final ResourcePool resourcePool = ResourcePool.INSTANCE;

    Manager() {
         final Producer producer = new Producer(resourcePool);
         final Consumer consumer = new Consumer(resourcePool);
        for (int i = 0; i < largerQuantity; i++) {
            if (i <= initialProducers) {
                this.clients.add(new Thread(producer, "Producer: " + i));
                this.clients.peekLast().start();
            }

            if (i <= initialConsumers) {
                this.clients.add(new Thread(consumer, "Consumer: " + i));
                this.clients.peekLast().start();
            }
        }
    }

    public void checkResources(int delayInterval) {

    }

    public void createClient(int resourcesDifferential) {
        if (resourcesDifferential > 0 ) {
            this.clients.add(new Thread(new Producer(resourcePool)));
        } else {
            this.clients.add(new Thread(new Consumer(resourcePool)));
        }
        this.clients.peek().start();
    }

    public void killClient(Runnable client) {

    }

    void shutdown() {
        for (Thread client : clients) {
            client.interrupt();
        }
    }

}
