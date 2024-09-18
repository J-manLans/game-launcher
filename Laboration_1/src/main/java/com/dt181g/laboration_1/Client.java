package com.dt181g.laboration_1;

public class Client extends Thread{
        private final Object clientLock = new Object();
        private boolean threadDone = false;
        private final ThreadManager manager;
        private WorkerThread workerThread;

        public Client(final String name) {
            this.setName(name);
            this.manager = ThreadManager.INSTANCE;
        }

        public void notifyWorksDone() {
            synchronized (clientLock) {
                threadDone = true;
                clientLock.notify();
            }
        }

        @Override
        public void run() {
            workerThread = manager.getThread();
            workerThread.setClient(this);

            System.out.println(Thread.currentThread().getName() + " executes " + workerThread.getName());
            workerThread.doWork();

            synchronized(clientLock) {
                while (!threadDone) {
                    try {
                        clientLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                workerThread.setClient(null);
            }

            System.out.println(workerThread.getName() + " is returned to the pool. " + this.getName() + " leaves the building.");
            manager.returnThread(workerThread);
        }
}
