package com.dt181g.laboration_1;

import java.util.Random;

/**
 * This class represents a worker thread that adds a layer of randomness to a number
 * provided by a client.
 * The worker thread can be in an idle state, waiting for a client to assign work,
 * and can be shut down when no longer needed.
 * @author Joel Lansgren
 */
public class WorkerThread extends Thread {
    private final Object threadLock = new Object();
    private Client client;
    private boolean shutdown = false;
    private boolean gotClient = false;
    private Random randomizer = new Random();

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
     */
    public void notifyToDoWork() {
        synchronized (this.threadLock) {
            this.gotClient = true;
            this.threadLock.notify();
        }
    }

    /**
     * The method adds an extra layer of randomness based on the provided number.
     *
     * @param randomNum the base number used to generate a random number.
     * @return the layered random number
     */
    private int doWork(final int randomNum) {
            return randomizer.nextInt(randomNum);
    }

    /**
     * Shuts down the worker thread by setting the shutdown flag and notifying it to stop waiting.
     * Once shutdown is called, the thread will exit the while loop without performing any tasks.
     */
    public void shutdown() {
        synchronized (this.threadLock) {
            this.shutdown = true;
            this.threadLock.notify();
        }
    }

    /**
     * The main execution loop of the worker thread.
     * The thread awaits notification, performs its task,
     * and then notifies the client when the work is done.
     * It keeps running until the shutdown flag is set to true.
     */
    @Override
    public void run() {
        while (!this.shutdown) {

            synchronized (this.threadLock) {
                // Wait for instructions
                while (!this.gotClient && !this.shutdown) {
                    try {
                        this.threadLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (this.client != null) {
                System.out.println(
                    String.format("%s adds a layer of randomness to %d...",
                        this.getName(),
                        client.getRandNum()
                    )
                );
                this.client.updateRandNum(this.doWork(client.getRandNum()));
                this.client.notifyWorksDone();
                this.gotClient = false;
            }

        } System.out.println(this.getName() + " shutting down...");
    }
}
