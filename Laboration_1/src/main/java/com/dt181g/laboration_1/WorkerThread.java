package com.dt181g.laboration_1;

public class WorkerThread extends Thread {
    private final Object threadLock = new Object();
    private Client client;
    private boolean shutdown = false;
    private boolean gotClient = false;

    public WorkerThread(String name) {
        this.setName(name);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void doWork() {
        synchronized (threadLock) {
            this.gotClient = true;
            this.threadLock.notify();
        }
    }

    public void shutdown() {
        this.shutdown = true;
        doWork();
    }

    @Override
    public void run() {
        while (!this.shutdown) {

            synchronized (this.threadLock) {
                while(!this.gotClient) {
                    try {
                        this.threadLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.gotClient = false;
            }

            if (this.client != null) {
            // Execute the task
                System.out.println(this.getName() + " performs monotonous task...");
                try {
                    Thread.sleep(300L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.client.notifyWorksDone();
            }

        } System.out.println(this.getName() + " shutting down...");
    }
}
