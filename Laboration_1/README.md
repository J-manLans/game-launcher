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
- Implement a simple thread manager for a thread pool containing 5 threads adhering to the **Object Pool Manager Pattern**.
- When a client request a thread the ownership should transfer from the manager to the client and revert back to the pool after its use.
- If the pool is empty the client must wait for a thread to be returned

## Procedures
### Main Method
**The first step** is to create the entrance point of the program, this is done in the Lab1 class main method. This method will be responsible for initiating a pool of simulated clients that will utilize the thread pool to obtain a multilayered randomized number. The main thread will wait for the client to do their thing in tandem with the worker threads and then shut down the thread pool.

**To facilitate this**, an ``ArrayList`` will be created with an initial capacity of 20 to store the clients (20 is the maximum number of clients that can randomly be generated upon program execution). A for loop will then be employed to populate this list with client instances giving them a name using the current index from the loop. The ``ArrayList`` is used for its simplicity, as no complex operations will be necessary for managing the clients. Furthermore, to simulate the clients as real entities competing for resources the program requires the ability to execute them as threads, enabling them to execute concurrently. This will be achieved by extending the client class with ``Thread``. When that is done the ``start()`` method of the ``Thread`` class can be called on each of the clients, also in a for-each loop.

**Next up** is to wait for the clients to complete their tasks with the help of the worker threads and then shut down the thread pool. This will be done in another for-each loop where the client joins the main thread, forcing it to wait for the client thread to terminate. When all clients are terminated statistics will be printed to the standard output and the ``ThreadManager`` singleton will utilize its ``shutdown()`` method to shut down the thread pool.

### Client Class
**This class needs** to be responsible for requesting threads from the pool to add a layer of randomness to their already generated random number. It will obtain ownership of a thread when granted access, and upon completing its task, it will return the thread to the pool.



## Discussion
### Purpose Fulfillment

### Alternative Approaches

## Personal Reflections
