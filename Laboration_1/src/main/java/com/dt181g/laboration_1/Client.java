package com.dt181g.laboration_1;

public class Client extends Thread{
        private final Object clientLock = new Object();
        private final ThreadManager manager;
        private Object poolLock;
        private WorkerThread workerThread;
        private Object threadLock;

        public Client(final String name) {
            this.setName(name);
            this.manager = ThreadManager.INSTANCE;
            this.poolLock = this.manager.getPoolLock();
        }

        public Object getClientLock() {
            return clientLock;
        }

        @Override
        public void run() {
            synchronized(clientLock) {
                workerThread = manager.getThread();
                threadLock = workerThread.getThreadLock();
                workerThread.setClient(this);

                System.out.println(Thread.currentThread().getName() + " utilizing " + workerThread.getName());

                synchronized (threadLock) {
                    threadLock.notify();
                }

                try {
                    clientLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (poolLock) {
                    manager.returnThread(workerThread);
                }

                System.out.println(workerThread.getName() + " is returned to the pool. Thread " + this.getName() + " terminates.");
            }
        }
}
