package com.dt181g.laboration_1;

import java.util.Deque;
import java.util.LinkedList;

/**
 * The {@code ThreadManager} class manages a pool of worker threads.
 * It provides threads for clients to use and returns them to the pool when they are finished.
 * It also handles the shutdown of all worker threads when needed.
 * The {@code ThreadManager} is implemented as a singleton, ensuring only one instance is created.
 * @author Joel Lansgren
 */
public final class ThreadManager {
    private final int threadPoolSize = 5;
    public static final ThreadManager INSTANCE = new ThreadManager();
    private final Deque<WorkerThread> workerThreads = new LinkedList<WorkerThread>();
    private final Object poolLock = new Object();
    private int threadUtilizations = 0;

    /**
     * Private constructor to prevent external instantiation.
     * Initializes the pool with 5 worker threads.
     */
    private ThreadManager() {
        for (int i = 1; i <= threadPoolSize; i++) {
            WorkerThread workerThread = new WorkerThread("Worker thread " + i);
            this.workerThreads.add(workerThread);
            workerThread.start();
        }
    }

    /**
     * Returns the number of thread utilizations.
     * @return the number of thread utilizations.
     */
    public int getThreadUtilizations() {
        return this.threadUtilizations;
    }

    /**
     * Retrieves a {@code WorkerThread} from the pool and add 1 to thread utilizations.
     * If no thread is available, the method will wait until a thread is returned to the pool.
     * @return a {@code WorkerThread} from the pool.
     */
    public WorkerThread getThread() {
        synchronized (this.poolLock) {
            while (this.workerThreads.isEmpty()) {
                try {
                    poolLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.threadUtilizations += 1;
            return this.workerThreads.pollFirst();
        }
    }

     /**
     * Returns a {@code WorkerThread} to the pool once it has completed its work.
     * @param workerThread the {@code WorkerThread} to be returned to the pool.
     */
    public void returnThread(final WorkerThread workerThread) {
        synchronized (poolLock) {
            workerThreads.add(workerThread);
            poolLock.notify();
        }
    }

     /**
     * Shuts down all the worker threads in the pool.
     * It ensures that all threads complete their current tasks before shutting down.
     * Once all threads are stopped, the pool is cleared.
     */
    public void shutdown() {
        for (WorkerThread workerThread : workerThreads) {
            workerThread.shutdown();
        }

        for (WorkerThread workerThread : workerThreads) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        workerThreads.clear();
        if (workerThreads.size() == 0) {
            System.out.println("\nThe Thread Pool is empty. Good work guys, see yah tomorrow!\n");
        }
    }
}
