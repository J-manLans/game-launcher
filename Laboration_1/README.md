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
The purpose of this program is to simulate a simple multithreaded environment where multiple clients request and share worker threads from a thread pool. Each client will provide a random number to the worker thread, which will add an additional layer of "randomness" to it, simulating a task. It demonstrates basic concurrency by showing how clients can use limited computational resources to perform tasks efficiently. This simulation is a simplified model of how thread pools can optimize performance and resource utilization in concurrent systems.

### Concrete Goals
- Simulate clients by having a list of threads that stand in line to utilize the worker threads.
- When a client request a thread the ownership should transfer from the manager to the client and revert back to the pool after its use.
- If the pool is empty the client must wait for a thread to be returned
- Implement a simple thread manager for a thread pool containing 5 threads adhering to the **Object Pool Manager Pattern**.

## Procedures
![Copilots view of this program](./src/main/resources/thread_pool.jpg)
### Main Method
**The main method** serves as the entry point for the application and just initiates the StoreFront. The StoreFront in turn is designed to simulate client interactions with worker threads managed by the ``ThreadManager``. It initiates the process by generating a random number of clients between 10 and 20. Each client is instantiated with a unique name and added to a list for processing.

Once the clients are created, the method activates each client in its own thread. This is done through the start method, which triggers the execution of their respective tasks using the available worker threads. The method then waits for all clients to complete their tasks by joining each thread, ensuring that the main thread only proceeds once all clients have finished.

After the clients have completed their work, the method prints a summary, detailing the number of clients and the total thread utilizations recorded by the ``ThreadManager``. Finally, it calls the ``shutdown`` method on the ``ThreadManager`` to gracefully terminate all worker threads, concluding the simulation.

### Client Class
`**The ``Client`` class**` needs to manage requests for threads from the thread pool, facilitating a layer of randomness to its generated numbers. When the pool is depleted, the client will wait for a thread to become available, gaining ownership of a thread upon access and returning it after task completion.

Extending the Thread class, the ``Client`` class must override its ``run`` method, which serves as the entry point for thread execution. Two additional methods must be implemented: the ``updateRandNum`` method updates the client’s random number, while the ``notifyWorksDone`` method manages signaling between the client and the worker thread.

**The ``updateRandNum`` method** shall set the ``randNum`` to the value provided by the worker’s ``doWork`` method. The ``notifyWorksDone`` method will handle communication between threads through synchronized blocks using a dedicated lock object (``clientLock``). This mechanism is essential for thread safety, ensuring that the client can correctly wait for the worker’s completion notification.

**In the ``run`` method**, the ``ThreadManager.INSTANCE`` needs to be utilized to acquire a worker thread through its ``getThread`` method, establishing the client as the thread’s owner. Following this, the client notifies the worker to proceed with its task of generating a random number, which will update the client’s ``randNum``.

Once the client has initiated the worker, it will enter a loop that monitors the ``workerThreadDone`` flag, which indicates whether the worker has completed its task. This flag is initially set to false and is updated to true by the worker in the ``notifyWorksDone`` method before notifying the client. The while loop maintains the client’s waiting state, accounting for potential spurious wakeups, ensuring that it only exits when the worker thread invokes ``notifyWorksDone``, signaling that its task is complete.

````java
synchronized (this.clientLock) {
    while (!this.workerThreadDone) {
        try {
            this.clientLock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
````

Once the worker has completed its task the client removes itself from the worker, returns the thread to the pool and the client is notified, the client prints the relevant statistics and clears its reference to the worker. Finally, the client thread terminates after executing all necessary actions.

### WorkerThread
The ``WorkerThread`` class will be responsible for executing tasks that add randomness to numbers received from its assigned client. This class operates within a pool of five threads managed by the ``ThreadManager``, which is tasked with initializing and starting these worker threads.

This class includes a couple of methods alongside the ``run`` method, each serving specific purposes. The ``setClient`` method will allow the client to assign itself to the worker. The ``doWork`` method will be responsible for randomizing the given number. The ``notifyToWork`` method will signal the worker that it needs to begin its task, while the ``shutdown`` method will notify the worker to terminate once all clients have completed their tasks. Other than that there is two getter methods that the thread manager uses to determine what thread to give away next.

The ``run`` method will be contained within a while loop that continually checks a shutdown flag, ensuring the thread remains active while clients require its services. When the ``shutdown`` method is invoked, the thread will exit gracefully.

Initially, the worker thread will enter a wait state using the synchronization mechanism described in the ``Client`` class.

Once released from the synchronized block, the worker will check if a client is assigned. If a client is present, the worker will call the client's ``updateRandNum`` method using its own ``doWork`` method to generate a new random number based on the one the client already provided:

````java
this.client.updateRandNum(doWork(this.clientsNum));
````

After completing the task, the worker will notify the client that the job is done and re-evaluate the shutdown flag to determine whether to re-enter the while loop.

### TreadManager
The ``ThreadManager`` class will be responsible for managing a fixed pool of worker threads, enabling clients to borrow threads for their tasks and returning them to the pool upon completion. This class will facilitate a graceful shutdown of all threads when no clients remain. It will follow the singleton design pattern, ensuring only one instance exists throughout the application.

The constructor will be private to enforce the singleton pattern, preventing external instantiation. During initialization, the class will create five ``WorkerThread`` instances, naming and starting them immediately. These threads will be added to an internal list for client access.

The primary methods in this class will include ``getThread``, ``returnThread``, and ``shutdown``. A common getter will also be implemented to provide data on thread utilization for statistical purposes.

The ``getThread`` method will allow retrieval of a ``WorkerThread`` from the pool. If no threads are available, the calling thread will wait until one is returned. This method will be synchronized on ``poolLock`` to ensure thread safety. When a thread becomes available, the method will increment the ``threadUtilizations`` counter and if the first thread in the pool had a client whose initial random number was prime, it will return a thread based on an index calculated of the modified client number, if the ``preferredWorkerIndex`` exceeds the current size of the pool, the first ``WorkerThread`` will be returned. and if the threads clients number wasn't prime that thread will be returned.

The ``returnThread`` method will also be synchronized on the ``poolLock``, notifying any waiting threads in ``getThread`` that a thread is now available for use.

The ``shutdown`` method will be invoked from the main program once all clients have completed their tasks. It will iterate over each worker thread, invoking their respective ``shutdown`` methods, and then wait for their termination using ``join``, ensuring the thread pool is cleared afterward.

### Additional Considerations

**There is also the option** to implement the ``Runnable`` interface for both the ``Client`` and ``WorkerThread`` classes. While the current design is straightforward, relying on direct thread extension, using ``Runnable`` could offer greater flexibility for future changes. For example, if we needed to extend the Client class or add new functionality.

A key advantage of using ``Runnable`` is its ability to promote a cleaner separation between task logic and threading mechanics. This allows for reusing the same ``Runnable`` instance across multiple threads, making it more memory-efficient, particularly in scenarios involving many threads. In contrast, extending the ``Thread`` class results in multiple instances of custom thread classes, leading to higher memory consumption. Each thread created from a class that extends ``Thread`` maintains its own state in addition to the base ``Thread`` class. By implementing ``Runnable``, only a single instance of the task's state is needed, which can be shared among multiple threads.

However, in this scenario where only a small number of clients and workers are used, and no resources are shared, refactoring the code to implement ``Runnable`` might involve to much work while providing limited benefits. Given the simplicity of the current implementation, the advantages of switching may not justify the added complexity at this time.

**Thread sleep** is good to use in testing scenarios to simulate some kind of work, or if we need a generate something based on a given time-span. Or it can be used as a buff in a game for example that will be active a certain time, however, this wouldn't be very accurate since the buff is determined by the speed of the CPU. And just because of that reason it can be good to generate a pseudo random number like in this lab. It can also be a reasonable way to poll for availability in a thread pool for example, but since it's hard to predict when a thread is available a better solution for that is to utilize ``wait()`` on a lock object (or ``this`` can be used if the lock isn't shared outside tha class) that a thread holds inside a synchronized block, like in this lab.

**Thread pools** are a great way to cap the resources used by a program, it is also a good way to maintain thread creation and termination to a minimum, which is a costly operation. This also means that if a thread is available it is ready to perform the task immediately without the latency involved in creating a new thread. It's also a good use of the systems resources. If a thread has no work to do it just sits there idle and wait for tasks, however there is a balance to consider. If the job put on the thread pool isn't up to par with the available threads the queue of jobs to be done will continue to grow indefinitely. But solutions exist to have a dynamic pool of threads to handle these kinds of issues. For instance, you can configure the pool to grow or shrink based on the workload, ensuring that resources are allocated efficiently while preventing the queue from growing indefinitely.

When it comes to creating a thread pool it's important to consider the load it will be put under, to few or ot many threads are not good as it wont utilize the system in the most efficient way. It's also important to make sure the threads become available right at startup by having them wait inside a synchronized block locked on an object, otherwise they will just be lost as soon as they are created. Sometimes it can be important to do some kind of initialization after the threads have been added to the pool, so if that is the case one can't start them up right at creation, so this aspect is also important to consider.

**On the topic of ownership.** This solution completely hands over the thread to the client, while it doesn't break encapsulation it's not exactly best practices considering the manager who's role should be more of a mediator. In an earlier edition of this solution that indeed was the functionality, but looking over the instructions of the lab, changes was made to what was thought to be more in line with what was asked for. However, the goal was misunderstood and having the client utilize intermediary methods would have aligned more with what was asked for. Additionally, instead of having the worker thread having a set task to do a ``Runnable`` task could have been passed from the client through the manager for the worker to perform. That way, the functionality of the worker thread could be kept more modular and not tied down to a specific scenario.

## Discussion
So the purpose of this lab was to simulate a multithreaded environment where multiple clients request work to be done and share worker threads through a common thread pool managed by a thread manager. The concrete goals will be evaluated below.

### Purpose Fulfillment
#### Simulate clients by having a list of threads that stand in line to utilize the worker threads.
By initializing clients to an ArrayDeque in the StoreFront class, then starting them and then having them wait in the thread managers getThread method for a worker thread to become available this goal can be considered achieved. By having the clients initialize an initial random number that they hand off to the worker thread to be processed, an additional layer of simulation to replicate a client/worker scenario is established.

#### When a client request a thread the ownership should transfer from the manager to the client and revert back to the pool after its use.
By having the managers getThread method return the actual thread that the client then stores as a variable free to use at the clients content, ownership indeed has been transferred. By notifying the worker to do work, then letting the client wait in a synchronized block until the worker is done and finally return the thread in the notifyWorksDone method, this part of the assignment can be considered fulfilled.

#### If the pool is empty the client must wait for a thread to be returned
By using a synchronized block within the getThread method, the client enters a while-loop that checks if the pool is empty. If the pool is empty, the client will wait until other clients return a thread. The wait method inside the synchronized block ensures that the client pauses execution while waiting for a thread to become available.

When another client returns a thread, the notify method will be triggered inside the synchronized block, waking up one of the waiting clients. This ensures that only one thread can access the shared thread pool at a time, avoiding race conditions or multiple clients trying to access the same thread pool simultaneously.

The use of synchronization prevents multiple threads from concurrently entering the same critical section of code, ensuring that only one thread accesses and modifies the pool at any given time. This mechanism successfully fulfills the requirement that the client must wait for a thread when none are available.

#### Implement a simple thread manager for a thread pool containing 5 threads adhering to the Object Pool Manager Pattern
The ThreadManager constructor creates a pool of 5 worker threads by instantiating them and adding them to a list. This ensures that the pool is ready for use as soon as the manager is instantiated. By pre-allocating these threads, the manager avoids the overhead of creating and destroying threads on each task request, which aligns with the object pool concept of reusing objects. By implementing methods for getting and returning threads like described above its functionality is complete. By letting clients get access to threads and giving them the options to return them with the use of synchronized blocks and wait/notify ensures that the threads are properly coordinated, making sure that clients can only access available threads and are notified when a thread is returned to the pool.

Thus, the ThreadManager fulfills the assignment's goal by adhering to the Object Pool Manager pattern, providing a thread pool that efficiently manages worker threads and coordinates their usage and return in a thread-safe manner.

### Alternative Approaches
modular tasks instead of predefined

using this as lock is possible since the lock isn't shared among threads

## Personal Reflections