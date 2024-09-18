package com.dt181g.laboration_1;

public class WorkerThread extends Thread {
    private final Object threadLock = new Object();
    private Client client;
    private boolean shutdown = false;

    public WorkerThread(String name) {
        this.setName(name);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void doWork() {
        synchronized (threadLock) {
            threadLock.notify();
        }
    }

    public void shutdown() {
        shutdown = true;
        doWork();
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

            if (client != null) {
                client.notifyWorksDone();
            }

        }
        System.out.println(this.getName() + " shutting down...");
    }
}
