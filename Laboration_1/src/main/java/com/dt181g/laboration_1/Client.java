package com.dt181g.laboration_1;

public class Client extends Thread{
        private final Object clientLock = new Object();
        private final ThreadManager manager;
        private WorkerThread workerThread;

        public Client(final String name) {
            this.setName(name);
            this.manager = ThreadManager.INSTANCE;
        }

        public void notifyWorksDone() {
            synchronized (clientLock) {
                clientLock.notify();
            }
        }

        @Override
        public void run() {
            workerThread = manager.getThread();
            workerThread.setClient(this);

            System.out.println(Thread.currentThread().getName() + " utilizing " + workerThread.getName());
            workerThread.doWork();

            synchronized(clientLock) {
                try {
                    clientLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            manager.returnThread(workerThread);
            System.out.println(workerThread.getName() + " is returned to the pool. " + this.getName() + " goes home.");
        }
}
