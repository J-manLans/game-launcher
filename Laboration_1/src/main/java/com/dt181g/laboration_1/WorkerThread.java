package com.dt181g.laboration_1;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class represents a worker thread that adds a layer of randomness to a number
 * provided by a client.
 * The worker thread can be in an idle state, waiting for a client to assign work,
 * and can be shut down when no longer needed.
 * @author Joel Lansgren
 */
public class WorkerThread extends Thread {
    private final Object workerThreadLock = new Object();
    private Client client;
    private int clientsNum;
    private boolean shutdown = false;
    private boolean gotClient = false;
    private Random randomizer;
    private AtomicBoolean counting = new AtomicBoolean();
    private int[] count = {0};
    private boolean isClientsNumPrime;

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
    public void setClient(final Client client, final boolean gotClient) {
        this.client = client;
        this.gotClient = gotClient;
    }

    /**
     * Returns the isPrime boolean
     * Utilized by the thread manager to determine if the thread has a prime number available
     * @return the isPrime boolean
     */
    public boolean isClientsNumPrime() {
        return this.isClientsNumPrime;
    }

    /**
     * Returns the clientsNum int.
     * Utilized by the thread manager to determine what thread to give away next
     * @return the clientsNum int
     */
    public int getClientsNum() {
        return this.clientsNum;
    }

    /**
     * Notifies the worker thread that it has work to do.
     */
    public void notifyToDoWork(int randNum) {
        synchronized (this.workerThreadLock) {
            this.clientsNum = randNum;
            this.workerThreadLock.notify();
        }
    }

    /**
     * Shuts down the worker thread by setting the shutdown flag and interrupting the thread.
     * Once shutdown is called, the thread will break out of the while loop through the catch block
     * without performing any tasks since the client is null and shutdown is true.
     */
    public void shutdown() {
        this.shutdown = true;
        this.interrupt();
    }

    /*===========================
     * Private methods
     *==========================/

    /**
     * The method checks if the initial number given by the client is prime with the helper method
     * {@code isRandPrime}.
     * If so, the number is flagged and additional computation is done. The helper method {@code threadSleepAndIncrement}
     * is utilized for this to add complexity to the upper bound for number generation to the {@code randomizer}.
     * The prime number is also used to set the randomizer's seed. At the start of the method the randomizer is
     * re-instantiated to prevent unpredictability.
     *
     * <p>
     * If the number isn't prime, a new random number is generated based on the initial number.
     * <p/>
     *
     * @param initialNum the base number used to generate a random number.
     * @return the layered random number
     */
    private int doWork(int initialNum) {
        randomizer = new Random();
        this.isRandPrime(initialNum);
        if (isClientsNumPrime) {
            randomizer.setSeed(initialNum);
            return randomizer.nextInt(threadSleepAndIncrement(initialNum));
        }
        return randomizer.nextInt(initialNum);
    }

    /**
     * The method checks if the initial number given by the client is prime and sets
     * the {@code isPrime} to false if not and true if it is
     *
     * @param initialNum the base number used to generate a random number.
     * @return the layered random number
     */
    private void isRandPrime(final int initialNum) {
        if (initialNum % 2 == 1) {
            for (int i = 3; i <= (int) Math.sqrt(initialNum); i += 2) {
                if (initialNum % i == 0) {
                    this.isClientsNumPrime = false;
                    return;
                }
            }
            this.isClientsNumPrime = true;
        } else {
            this.isClientsNumPrime = false;
        }
    }

    /**
     * A helper method that increments a counter from a Runnable background thread while the worker thread is sleeping.
     * The sleep time of the worker is determined on the prime number put into the method as an argument.
     * When the worker wakes up it sets the atomic boolean {@code counting} to false to indicate to the
     * background thread that it can stop counting and terminate, then returns the incremented number.
     *
     * @param prime the initial number from the client when it is prime
     * @return a new number that serve as upper bound for the new random number to be generated
     */
    private int threadSleepAndIncrement(final int prime) {
        counting.set(true);
        count[0] = 1;  // Safety to prevent un-positive numbers
        new Thread(() -> {
            while (counting.get()) {
                count[0]++;
            }
        }).start();

        try {
            Thread.sleep(prime);
            // Waiting for atoms to decay...
            counting.set(false);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        this.clientsNum = count[0];
        return count[0];
    }

    /*===========================
     * Run
     *==========================/

    /**
     * The main execution loop of the worker thread.
     * The thread awaits notification, performs its task,
     * and then notifies the client when the work is done.
     * It keeps running until the shutdown flag is set to true.
     */
    @Override
    public void run() {
        while (!this.shutdown) {

            synchronized (this.workerThreadLock) {
                // Wait for instructions
                while (!this.gotClient) {
                    try {
                        this.workerThreadLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            // The if block is used for the shutdown, when the threads get released for the day
            // by the storefront and no clients are available.
            if (this.client != null) {
                System.out.println(
                    String.format("%s adds a layer of randomness to %d...",
                        getName(),
                        this.clientsNum
                    )
                );
                this.client.updateRandNum(doWork(this.clientsNum));
                this.client.notifyWorksDone();
            }

        } System.out.println(this.getName() + " shutting down...");
    }
}
