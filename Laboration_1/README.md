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
- Implement a simple thread manager for a thread pool containing 5 threads adhering to the **Object Pool Manager Pattern**.
- When a client request a thread the ownership should transfer from the manager to the client and revert back to the pool after its use.
- If the pool is empty the client must wait for a thread to be returned

## Procedures

## Discussion
### Purpose Fulfillment

### Alternative Approaches

## Personal Reflections
