# Laboration 1

## Environment & Tools
### Hardware
* Processor: AMD Ryzen 7 5800H - 3.20 GHz
* Installed RAM: 16,0 GB
* System type: 64-bit operating system, x64-based processor - Windows 11
* NVIDIA GeForce RTX 3060 Laptop GPU
* NVMe SAMSUNG MZVLQ512 - 475GB
### Software
* **Vs Code** version 1.93.1
* **Git** version 2.43.0.windows.1
* **Git Bash**
* **Bitbucket**
* **Apache Maven** version 3.9.6
* **Windows**
    * Edition Windows 11 Home
    * Version 23H2
    * OS build 22631.4169
    * Experience Windows Feature Experience Pack 1000.22700.1034.0
* **Java**:
    * openjdk version "21.0.2" 2024-01-16 LTS
    * OpenJDK Runtime Environment Temurin-21.0.2+13 (build 21.0.2+13-LTS)
    * OpenJDK 64-Bit Server VM Temurin-21.0.2+13 (build 21.0.2+13-LTS, mixed mode, sharing)

## Purpose
The purpose of this program is to simulate a simple multithreaded environment where multiple clients request and share worker threads from a thread pool. Each client will provide a random number to the worker thread, which will add an additional layer of randomness to it, simulating a task. It demonstrates basic concurrency by showing how clients can use limited computational resources to perform tasks efficiently. This simulation is a simplified model of how thread pools can optimize performance and resource utilization in concurrent systems.

### Concrete Goals
- Simulate clients by having a pool of threads that stand in line to utilize the worker threads.
- When a client request a thread the ownership should transfer from the manager to the client and revert back to the pool after its use.
- If the pool is empty the client must wait for a thread to be returned
- Implement a simple thread manager for a thread pool containing 5 threads adhering to the **Object Pool Manager Pattern**.

## Procedures
### Main Method
**The first step** is to create the entrance point of the program, this is done in the Lab1 class main method. This method will be responsible for initiating a pool of simulated clients that will utilize the thread pool to obtain a multilayered randomized number. The main thread will wait for the client to do their thing in tandem with the worker threads and then shut down the thread pool.

**To facilitate this**, an ``ArrayList`` will be created with an initial capacity of 20 to store the clients (20 is the maximum number of clients that can randomly be generated upon program execution). A for loop will then be employed to populate this list with client instances giving them a name using the current index from the loop and pass it into the constructor of the client which uses the ``setName`` method from the ``Thread`` class to set its name. The ``ArrayList`` is used for its simplicity, as no complex operations will be necessary for managing the clients. Furthermore, to simulate the clients as real entities competing for resources the program requires the ability to execute them as threads, enabling them to execute concurrently. This will be achieved by extending the ``Client`` class from ``Thread``. Once that is done, the ``start()`` method of the ``Thread`` class can be called on each client, which in turn starts a new thread and invokes the ``run()`` method within that thread. This process will be implemented in a for-each loop.

**Next up** is to wait for the clients to complete their tasks with the help of the worker threads and then shut down the thread pool. This will be done in another for-each loop where the client joins the main thread, forcing it to wait for the client thread to terminate. When all clients are terminated statistics will be printed to the standard output and the ``ThreadManager`` singleton will utilize its ``shutdown()`` method to shut down the thread pool.

### Client Class
**This class needs** to be responsible for requesting and obtaining threads from the thread pool to add a layer of randomness to their already randomly generated number. If the pool is empty the clients must wait until a thread is returned. It will obtain ownership of a thread when granted access, and upon completing its task, it will return the thread to the pool.

**The ``Client`` class** extends ``Thread`` and must therefore overrun its ``run()`` method that represent the entry point for the threads execution since it by default does nothing. Additionally two other methods needs to be implemented to update the random number and notify the client that the worker thread is done.

**The private ``updateRandNum()`` method** will simply set its variable to the parameter passed into the method by the worker ``doWork()`` method executed in the clients ``run()`` method.

````java
updateRandNum(this.workerThread.doWork(randNum));
````

The ``notifyWorksDone()`` method however, demands a little more explanation. It needs to handle the signaling between the client and worker thread, letting the client know that the worker thread is done.

**To achieve this** two synchronized blocks will be used together with a dedicated lock object (``clientLock``) This is crucial for maintaining thread safety during the signaling process. By creating a separate ``Object`` instance (``clientLock``) and using its intrinsic lock, we ensure that both the client’s waiting state and the worker’s notification are synchronized using the same lock object, thus allowing proper communication between the two synchronized blocks. When a worker thread calls the method and enters the synchronized block inside, it holds the lock on ``clientLock``, which was previously released by the client in its ``run()`` method when it entered the wait state. Now the second part of the synchronization mechanism comes into play.

**In the client’s ``run()`` method**, the client will first enter a while loop that checks the boolean flag ``threadDone`` before entering its wait state. This flag, initially set to false, is changed to true by the worker thread before it notifies the client. The loop ensures that the client remains in the waiting state until the worker thread actually sends the notification. This is necessary because of something called spurious wakeups, where a thread might exit the wait state even though ``notify()`` was not called. To prevent premature execution, the while loop continuously checks the ``threadDone`` flag. The client only breaks out of this loop when the worker thread calls ``notifyWorksDone()``, changing the flag to true within its synchronized block.

````java
synchronized (clientLock) {
    while (!threadDone) {
        try {
            this.clientLock.wait(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    this.workerThread.setClient(null);
}
````

This ensures that the client proceeds only after the worker thread has completed its task, guaranteeing correct behavior in a multithreaded environment.

**The only thing left now is the ``run()`` method.** A lot has already been covered, but lets take it from the top. This method needs to handle the synchronization between the worker and client thread. To achieve this the ``ThreadManager.INSTANCE`` needs to be utilized to give access to worker threads. By executing its ``getThread()`` method the client obtains ownership over a worker thread and set itself as its client, to give the worker the capability to notify the client when the work is done.

## Discussion
### Purpose Fulfillment

### Alternative Approaches

## Personal Reflections
