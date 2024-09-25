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
        if (clients.size() <= initialConsumers + initialProducers) {
            this.clients.add(new Thread(client, type));
            this.clients.peekLast().start();
        }

        for (Thread thread : clients) {
            if (!thread.getName().equals(type)) {
                thread.interrupt();
                this.clients.remove(thread);
                break;
            }
        }
    }

    synchronized void shutdown() {
        for (Thread thread : clients) {
            thread.interrupt();
        }
    }
}
