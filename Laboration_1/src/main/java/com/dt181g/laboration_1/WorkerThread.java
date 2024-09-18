package com.dt181g.laboration_1;

public class WorkerThread extends Thread {
    private final Object threadLock = new Object();
    private Client client;
    private Object clientLock;
    private boolean shutdown = false;

    public WorkerThread(String name) {
        this.setName(name);
    }

    public Object getThreadLock() {
        return threadLock;
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientLock = this.client.getClientLock();
    }

    public void shutdown() {
        shutdown = true;
        synchronized (threadLock) {
            threadLock.notify();
        }
    }

    @Override
    public void run() {
        while (true) {

            synchronized (threadLock) {
                try {
                    threadLock.wait();
                    if (shutdown) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Execute the task
            System.out.println(this.getName() + " Fetching resources...");
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (clientLock) {
                if (client != null) {
                    clientLock.notify();
                }
            }

        }
        System.out.println(this.getName() + " shutting down...");
    }
}
