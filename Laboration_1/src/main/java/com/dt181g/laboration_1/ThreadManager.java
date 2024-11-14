package com.dt181g.laboration_1;

import java.util.ArrayList;

/**
 * The {@code ThreadManager} class manages a pool of worker threads.
 * It provides threads for clients to use and returns them to the pool when they are finished.
 * It also handles the shutdown of all worker threads when needed.
 * The {@code ThreadManager} is implemented as a singleton, ensuring only one instance is created.
 * @author Joel Lansgren
 */
public enum ThreadManager {
    INSTANCE;
    private final int threadPoolSize = 5;
    private final ArrayList<WorkerThread> workerThreadPool = new ArrayList<WorkerThread>(5);
    private WorkerThread workerThread;
    private final Object poolLock = new Object();
    private int threadUtilizations;
    private int preferredWorkerIndex;

    /**
     * Private constructor to prevent external instantiation.
     * Initializes the pool with 5 worker threads.
     */
    private ThreadManager() {
        for (int i = 1; i <= threadPoolSize; i++) {
            this.workerThreadPool.add(new WorkerThread("Worker thread " + i));
            workerThreadPool.get(workerThreadPool.size() - 1).start();
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
     * Retrieves a {@code WorkerThread} from the pool and increments the thread utilization count.
     * <p>
     * If no thread is available, the method will wait until a thread is returned to the pool.
     * Initially, the first worker thread in the pool is selected. If the threads clients initial number
     * was prime, a new thread will be chosen. Based on the selected thread's
     * modified client number, a preferred index is calculated to determine which worker thread
     * to return on subsequent calls. If the preferred index (determined by a modulus operation)
     * exceeds the current pool size, the method defaults to returning the first worker.
     * </p>
     *
     * @return a {@code WorkerThread} from the pool.
     */
    public WorkerThread getThread() {
        synchronized (this.poolLock) {
            while (this.workerThreadPool.isEmpty()) {
                try {
                    poolLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.threadUtilizations++;
            workerThread = this.workerThreadPool.get(0);
            if (workerThread.isClientsNumPrime()) {
                preferredWorkerIndex = workerThread.getClientsNum() % 5;
                return this.workerThreadPool.remove(preferredWorkerIndex < workerThreadPool.size() ? preferredWorkerIndex : 0);
            } else { return this.workerThreadPool.remove(0); }
        }
    }

     /**
     * Returns a {@code WorkerThread} to the pool once it has completed its work.
     * @param workerThread the {@code WorkerThread} to be returned to the pool.
     */
    public void returnThread(final WorkerThread workerThread) {
        synchronized (poolLock) {
            workerThreadPool.add(workerThread);
            poolLock.notify();
        }
    }

     /**
     * Shuts down all the worker threads in the pool.
     * It ensures that all threads complete their current tasks before shutting down.
     * Once all threads are stopped, the pool is cleared.
     */
    public void shutdown() {
        for (WorkerThread workerThread : workerThreadPool) {
            workerThread.shutdown();
        }

        for (WorkerThread workerThread : workerThreadPool) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        workerThreadPool.clear();
    }
}
