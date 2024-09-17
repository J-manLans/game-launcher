package com.dt181g.laboration_1;

import java.util.Deque;
import java.util.LinkedList;

public class ThreadManager {
    public static final ThreadManager INSTANCE = new ThreadManager();
    private final Deque<WorkerThread> workerThreads = new LinkedList<WorkerThread>();
    private final Object poolLock = new Object();

    private ThreadManager() {
        for (int i = 1; i <= 5; i++) {
            WorkerThread workerThread = new WorkerThread("Worker thread " + i);
            workerThreads.add(workerThread);
            workerThread.start();
        }
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
            return workerThreads.pollFirst();
        }
    }

    public synchronized Object getPoolLock() {
        return poolLock;
    }

    public synchronized boolean hasThread() {
        return !workerThreads.isEmpty();
    }

    public void returnThread(WorkerThread workerThread) {
        synchronized (poolLock) {
            workerThreads.add(workerThread);
            poolLock.notify();
        }
    }
}
