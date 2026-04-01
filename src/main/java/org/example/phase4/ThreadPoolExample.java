package org.example.phase4;

import java.util.concurrent.*;

public class ThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {

        // Fixed thread pool (2 threads only)
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Alternative:
        // ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            executor.submit(() -> {
                try {
                    System.out.println(
                            "Executing Task " + taskId +
                                    " by " + Thread.currentThread().getName()
                    );

                    // simulate work
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // stop accepting new tasks
        executor.shutdown();

        // wait for tasks to finish
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("All tasks completed.");
    }
}

/*

    Q1 : Why not create threads manually?

    Answer:
    Thread creation is expensive in terms of memory and CPU.
    Creating too many threads can cause excessive context switching.
    Thread pools reuse existing threads, improving performance and resource management.

    ----------------------------------------------------

    Q2 : What queue does ThreadPoolExecutor use?

    Answer:
    ThreadPoolExecutor internally uses a BlockingQueue
    to store tasks when all worker threads are busy.

    Common implementations:

    ArrayBlockingQueue
    LinkedBlockingQueue
    SynchronousQueue

    ----------------------------------------------------

    Q3 : What happens if all threads are busy?

    Answer:

    1. Tasks are placed into the BlockingQueue.
    2. If the queue becomes full:
       - new threads are created up to maximumPoolSize
    3. If the queue is full AND max threads reached:
       - task is handled by RejectedExecutionHandler.

    ----------------------------------------------------

    Architecture:

    Client Request
          ↓
    ThreadPoolExecutor
          ↓
    BlockingQueue
          ↓
    Worker Threads

    ----------------------------------------------------

    Q4 : Difference between newFixedThreadPool and newCachedThreadPool

    | Feature       | newFixedThreadPool                | newCachedThreadPool       |
    |---------------|-----------------------------------|---------------------------|
    | Thread Count  | Fixed number                      | Dynamic                   |
    | Queue Type    | LinkedBlockingQueue (unbounded)   | SynchronousQueue          |
    | Thread Reuse  | Yes                               | Yes                       |
    | Thread Growth | Limited                           | Can grow very large       |
    | Risk          | Queue may grow large              | Too many threads possible |
    | Best Use Case | Controlled workloads              | Short-lived async tasks   |

    ----------------------------------------------------

    Example:

    newFixedThreadPool(2)

    Tasks run like:

    Task1 -> Thread1
    Task2 -> Thread2
    Task3 -> Queue
    Task4 -> Queue
    ...

    ----------------------------------------------------

    newCachedThreadPool()

    Tasks run like:

    Task1 -> Thread1
    Task2 -> Thread2
    Task3 -> Thread3
    Task4 -> Thread4
    ...

    Threads are created dynamically and reused when idle.

====================================================
*/