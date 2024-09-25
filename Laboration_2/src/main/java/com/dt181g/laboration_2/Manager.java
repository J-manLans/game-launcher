package com.dt181g.laboration_2;

import java.util.LinkedList;

public enum Manager {
    INSTANCE;

    private final LinkedList<Thread> clients = new LinkedList<>();
    private final int initialProducers = 6;
    private final int initialConsumers = 5;
    private final int largerQuantity = Math.max(initialProducers, initialConsumers);
    private final ResourcePool resourcePool = ResourcePool.INSTANCE;
    final Producer producer = new Producer(resourcePool);
    final Consumer consumer = new Consumer(resourcePool);
    private final int startingPoolSize = resourcePool.getResources();

    Manager() {
        for (int i = 1; i <= largerQuantity; i++) {
            if (i <= initialProducers) {
                this.clients.add(new Thread(producer, "Producer"));
                clients.peekLast().start();
            }

            if (i <= initialConsumers) {
                this.clients.add(new Thread(consumer, "Consumer"));
                clients.peekLast().start();
            }
        }
    }

    public void checkResources() {
        /*
        * If the pool dwindles,the system will prioritize adding more producers
        * while reducing consumers. Conversely, if the pool swells, it will
        * increase consumers and decrease producers. This dynamic balance
        * ensures the simulation runs indefinitely, halting only upon user intervention.
        */
        int current;
        while(true) {
            current = resourcePool.getResources();
            if (current < startingPoolSize) {
                createClient(producer, "Producer");
                killClient("Consumer");
            } else {
                createClient(consumer, "Consumer");
                killClient("Producer");
            }
            System.out.println(resourcePool.getResources() + " Clients: " + clients.size());

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void createClient(Runnable client, String type) {
        this.clients.add(new Thread(client, type));
    }

    public void killClient(String client) {
        for (Thread thread : clients) {
            if (thread.getName().equals(client)) {
                this.clients.remove(thread);
                break;
            }
        }
    }

    void shutdown() {
        for (Thread client : clients) {
            client.interrupt();
        }
    }
}
