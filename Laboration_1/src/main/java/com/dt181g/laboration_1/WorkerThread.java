package com.dt181g.laboration_1;

/**
 * This class represents a worker thread that performs tasks assigned by a client.
 * The worker thread can be in an idle state, waiting for a client to assign work,
 * and can be shut down when no longer needed.
 * @author Joel Lansgren
 */
public class WorkerThread extends Thread {
    private final Object threadLock = new Object();
    private Client client;
    private boolean shutdown = false;
    private boolean gotClient = false;
    private final long simulateWork = 300L;

    /**
     * Constructs a WorkerThread with a given name.
     * @param name the name of the worker thread
     */
    public WorkerThread(final String name) {
        this.setName(name);
    }

    /**
     * Sets the client for this worker thread. The client assigns tasks to the worker.
     * @param client the client assigning tasks to the worker
     */
    public void setClient(final Client client) {
        this.client = client;
    }

    /**
     * Notifies the worker thread that it has work to do.
     * This method is called when the client assigns a task to the worker.
     */
    public void doWork() {
        synchronized (threadLock) {
            this.gotClient = true;
            this.threadLock.notify();
        }
    }

    /**
     * Shuts down the worker thread by setting the shutdown flag and notifying it to stop waiting.
     * Once shutdown is called, the thread will exit the while loop without performing any tasks.
     */
    public void shutdown() {
        this.shutdown = true;
        doWork();
    }

    /**
     * The main execution loop of the worker thread.
     * The thread waits for tasks, performs the assigned work,
     * and then notifies the client when the work is done.
     * It keeps running until the shutdown flag is set to true.
     */
    @Override
    public void run() {
        while (!this.shutdown) {

            synchronized (this.threadLock) {
                // Wait for instructions
                while (!this.gotClient) {
                    try {
                        this.threadLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.gotClient = false;
            }

            if (this.client != null) {
                // Execute the predefined task
                System.out.println(this.getName() + " performs monotonous task...");
                try {
                    Thread.sleep(simulateWork);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.client.notifyWorksDone();
            }

        } System.out.println(this.getName() + " shutting down...");
    }
}
