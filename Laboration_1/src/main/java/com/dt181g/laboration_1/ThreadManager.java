package com.dt181g.laboration_1;

import java.util.Deque;
import java.util.LinkedList;

public class ThreadManager {
    public static final ThreadManager INSTANCE = new ThreadManager();
    private final Deque<WorkerThread> workerThreads = new LinkedList<WorkerThread>();
    private final Object poolLock = new Object();
    private boolean isEmpty;
    private int threadUtilizations = 0;

    private ThreadManager() {
        for (int i = 1; i <= 5; i++) {
            WorkerThread workerThread = new WorkerThread("Worker thread " + i);
            workerThreads.add(workerThread);
            workerThread.start();
        }
    }

    public int getThreadUtilizations() {
        return threadUtilizations;
    }

    public WorkerThread getThread() {
        synchronized(poolLock) {
            while (workerThreads.peekFirst() == null) {
                try {
                    poolLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threadUtilizations += 1;
            return workerThreads.pollFirst();
        }
    }

    public void returnThread(WorkerThread workerThread) {
        synchronized (poolLock) {
            isEmpty = workerThreads.isEmpty();
            workerThreads.add(workerThread);
            if (isEmpty) {} poolLock.notify();
        }
    }

    public void shutdown() {
        for (WorkerThread workerThread : workerThreads) {
            workerThread.shutdown();
        }
    }
}
