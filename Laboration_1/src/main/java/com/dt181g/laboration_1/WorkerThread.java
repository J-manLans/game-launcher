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
    Random randomizer = new Random();

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
     * Notifies the worker thread that it has work to do and executes the task.
     * The method adds an extra layer of randomness based on the provided number.
     * After the work is done, it notifies the client that the task has been completed.
     *
     * @param randomNum the base number used to generate a random number.
     */
    public void doWork(int randomNum) {
        synchronized (threadLock) {
            this.gotClient = true;
            System.out.println(
                String.format("%s adds a layer of randomness to %d...",
                    this.getName(),
                    randomNum
                )
            );
            this.client.setRandomNum(randomizer.nextInt(randomNum));
            this.threadLock.notify();
        }
    }

    /**
     * Shuts down the worker thread by setting the shutdown flag and notifying it to stop waiting.
     * Once shutdown is called, the thread will exit the while loop without performing any tasks.
     */
    public void shutdown() {
        synchronized (threadLock) {
            this.shutdown = true;
            this.threadLock.notify();
        }
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
                while (!this.gotClient && shutdown == false) {
                    try {
                        this.threadLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.gotClient = false;
            }

            if (this.client != null) {
                // notify client that work is done
                this.client.notifyWorksDone();
            }

        } System.out.println(this.getName() + " shutting down...");
    }
}
