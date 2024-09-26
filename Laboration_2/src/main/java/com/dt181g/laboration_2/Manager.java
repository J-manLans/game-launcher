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
    private int currentPoolSize;

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
        while(true) {
            currentPoolSize = resourcePool.getResources();

            if (currentPoolSize < startingPoolSize) {
                this.modifyClients(producer, "Producer");
            } else {
                this.modifyClients(consumer, "Consumer");
            }

            System.out.println(
                String.format(
                    "Resources: %d - Clients: %d",
                    resourcePool.getResources(),
                    clients.size()
                )
            );

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void modifyClients(Runnable client, String type) {
        for (Thread thread : clients) {
            if (!thread.getName().equals(type)) {
                thread.interrupt();
                this.clients.remove(thread);
                break;
            }
        }

        if (clients.size() < initialConsumers + initialProducers) {
            this.clients.add(new Thread(client, type));
            this.clients.peekLast().start();
        }
    }

    synchronized void shutdown() {
        for (Thread thread : clients) {
            thread.interrupt();
        }

        for (Thread thread : clients) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        clients.clear();
    }
}
