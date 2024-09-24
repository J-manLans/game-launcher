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
**The main method** serves as the entry point for the application, designed to simulate client interactions with worker threads managed by the ``ThreadManager``. It initiates the process by generating a random number of clients between 10 and 20. Each client is instantiated with a unique name and added to a list for processing.

Once the clients are created, the method activates each client in its own thread. This is done through the start method, which triggers the execution of their respective tasks using the available worker threads. The method then waits for all clients to complete their tasks by joining each thread, ensuring that the main thread only proceeds once all clients have finished.

After the clients have completed their work, the method prints a summary, detailing the number of clients and the total thread utilizations recorded by the ``ThreadManager``. Finally, it calls the ``shutdown`` method on the ``ThreadManager`` to gracefully terminate all worker threads, concluding the simulation.

### Client Class
`**The ``Client`` class**` needs to manage requests for threads from the thread pool, facilitating a layer of randomness to its generated numbers. When the pool is depleted, the client will wait for a thread to become available, gaining ownership of a thread upon access and returning it after task completion.

Extending the Thread class, the ``Client`` class must override its ``run`` method, which serves as the entry point for thread execution. Two additional methods must be implemented: the ``updateRandNum`` method updates the client’s random number, while the ``notifyWorksDone`` method manages signaling between the client and the worker thread.

**The ``updateRandNum`` method** shall set the ``randNum`` to the value provided by the worker’s ``doWork`` method. The ``notifyWorksDone`` method will handle communication between threads through synchronized blocks using a dedicated lock object (``clientLock``). This mechanism is essential for thread safety, ensuring that the client can correctly wait for the worker’s completion notification.

**In the ``run`` method**, the ``ThreadManager.INSTANCE`` needs to be utilized to acquire a worker thread through its ``getThread`` method, establishing the client as the thread’s owner. Following this, the client notifies the worker to proceed with its task of generating a random number, which will update the client’s ``randNum``.

Once the client has initiated the worker, it will enter a loop that monitors the ``threadDone`` flag, which indicates whether the worker has completed its task. This flag is initially set to false and is updated to true by the worker in the ``notifyWorksDone`` method before notifying the client. The while loop maintains the client’s waiting state, accounting for potential spurious wakeups, ensuring that it only exits when the worker thread invokes ``notifyWorksDone``, signaling that its task is complete.

````java
synchronized (clientLock) {
    while (!threadDone) {
        try {
            this.clientLock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    this.workerThread.setClient(null);
}
````

Once the worker has completed its task and the client is notified, the client prints the relevant statistics, unsets itself as the worker’s client, returns the worker thread to the manager, and clears its reference to the worker. Finally, the client thread terminates after executing all necessary actions.

### WorkerThread
The ``WorkerThread`` class will be responsible for executing tasks that add randomness to numbers received from its assigned client. This class operates within a pool of five threads managed by the ``ThreadManager``, which is tasked with initializing and starting these worker threads.

This class includes four methods alongside the ``run`` method, each serving specific purposes. The ``setClient`` method will allow the client to assign itself to the worker. The ``doWork`` method will be responsible for randomizing the given number. The ``notifyToWork`` method will signal the worker that it needs to begin its task, while the ``shutdown`` method will notify the worker to terminate once all clients have completed their tasks.

The ``run`` method will be contained within a while loop that continually checks a shutdown flag, ensuring the thread remains active while clients require its services. When the ``shutdown`` method is invoked, the thread will exit gracefully.

Initially, the worker thread will enter a wait state using the synchronization mechanism established in the ``Client`` class.

Once released from the synchronized block, the worker will check if a client is assigned. If a client is present, the worker will call the client's ``updateRandNum`` method using its own ``doWork`` method to generate a new random number:

````java
this.client.updateRandNum(this.doWork(client.getRandNum()));
````

After completing the task, the worker will notify the client that the job is done, revert the ``gotClient`` boolean back to false, and re-evaluate the shutdown flag to determine whether to re-enter the while loop.

### TreadManager
The ``ThreadManager`` class will be responsible for managing a fixed pool of worker threads, enabling clients to borrow threads for their tasks and returning them to the pool upon completion. This class will facilitate a graceful shutdown of all threads when no clients remain. It will follow the singleton design pattern, ensuring only one instance exists throughout the application.

The constructor will be private to enforce the singleton pattern, preventing external instantiation. During initialization, the class will create five ``WorkerThread`` instances, naming and starting them immediately. These threads will be added to an internal list for client access.

The primary methods in this class will include ``getThread``, ``returnThread``, and ``shutdown``. A common getter will also be implemented to provide data on thread utilization for statistical purposes.

The ``getThread`` method will allow retrieval of a ``WorkerThread`` from the pool. If no threads are available, the calling thread will wait until one is returned. This method will be synchronized on ``poolLock`` to ensure thread safety. When a thread becomes available, the method will increment the ``threadUtilizations`` counter and remove the first ``WorkerThread`` from the pool.

The ``returnThread`` method will also be synchronized on the ``poolLock``, notifying any waiting threads in ``getThread`` that a thread is now available for use.

The ``shutdown`` method will be invoked from the main program once all clients have completed their tasks. It will iterate over each worker thread, invoking their respective ``shutdown`` methods, and then wait for their termination using ``join``, ensuring the thread pool is cleared afterward.

### Additional Considerations

**There is also the option** to implement the ``Runnable`` interface for both the ``Client`` and ``WorkerThread`` classes. While the current design is straightforward, relying on direct thread extension, using ``Runnable`` could offer greater flexibility for future changes. For example, if we needed to extend the Client class or add new functionality.

A key advantage of using ``Runnable`` is its ability to promote a cleaner separation between task logic and threading mechanics. This allows for reusing the same ``Runnable`` instance across multiple threads, making it more memory-efficient, particularly in scenarios involving many threads. In contrast, extending the ``Thread`` class results in multiple instances of custom thread classes, leading to higher memory consumption. Each thread created from a class that extends ``Thread`` maintains its own state in addition to the base ``Thread`` class. By implementing ``Runnable``, only a single instance of the task's state is needed, which can be shared among multiple threads.

However, in this scenario where only a small number of clients and workers are used, and no resources are shared, refactoring the code to implement ``Runnable`` might involve to much work while providing limited benefits. Given the simplicity of the current implementation, the advantages of switching may not justify the added complexity at this time.

## Discussion
### Purpose Fulfillment

### Alternative Approaches

## Personal Reflections