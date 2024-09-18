package com.dt181g.laboration_1;

/**
 * The {@code Client} class represents a client that requests and utilizes a {@code WorkerThread}
 * from the {@code ThreadManager}. Once the client has finished utilizing the thread, it returns
 * the thread back to the manager.
 * Each {@code Client} runs in its own thread and interacts with a {@code WorkerThread} to perform tasks.
 * @author Joel Lansgren
 */
public class Client extends Thread {
        private final Object clientLock = new Object();
        private boolean threadDone = false;
        private final ThreadManager manager = ThreadManager.INSTANCE;
        private WorkerThread workerThread;

        /**
         * Constructs a new {@code Client} with the given name.
         * @param name the name of the client.
         */
        public Client(final String name) {
            this.setName(name);
        }

        /**
         * Notifies the client that the {@code WorkerThread} has finished its task.
         * This method is called by the {@code WorkerThread} when it completes its work.
         */
        public void notifyWorksDone() {
            synchronized (clientLock) {
                threadDone = true;
                clientLock.notify();
            }
        }

        /**
         * The {@code Client}'s main logic. The client retrieves a {@code WorkerThread} from the manager,
         * assigns itself to the thread, and waits for the thread to complete its work before returning the thread
         * to the pool.
         */
        @Override
        public void run() {
            workerThread = manager.getThread();
            workerThread.setClient(this);

            System.out.println(Thread.currentThread().getName() + " executes " + workerThread.getName());
            workerThread.doWork();

            synchronized (clientLock) {
                while (!threadDone) {
                    try {
                        clientLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                workerThread.setClient(null);
            }

            System.out.println(
                String.format(
                    "%s is returned to the pool. %s leaves the building.",
                    workerThread.getName(),
                    this.getName()
                )
            );
            manager.returnThread(workerThread);
        }
}
