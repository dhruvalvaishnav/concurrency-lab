package org.example.phase4;

import java.util.concurrent.*;

public class ThreadPoolMaxCorePoolSizeExample {
    public static void main(String[] args) {

        // Example of AbortPolicy → throws RejectedExecutionException
        ExecutorService executorService =
                new ThreadPoolExecutor(
                        2,
                        4,
                        60,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(2),
                        new ThreadPoolExecutor.AbortPolicy()
                );
        // threads = 4 (max reached),
        // queue = full
        // then AbortPolicy → throws RejectedExecutionException

        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            try {
                executorService.submit(() -> {

                    System.out.println("Executing Task " + taskId + " by " + Thread.currentThread().getName());

                    try {
                        Thread.sleep(2000); // slow task to fill queue
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });

            } catch (RejectedExecutionException e) {
                System.out.println("Task " + taskId + " rejected!");
            }
        }
        executorService.shutdown();
    }
}

/*
    Q1 : What happens if the queue is full in a ThreadPool?
    Answer: RejectedExecutionHandler

        Inside a ThreadPoolExecutor, tasks are handled like this:

        1. If running threads < corePoolSize → create new thread
        2. Else → put task into queue
        3. If queue is full → create thread up to maximumPoolSize
        4. If max threads reached AND queue full → task rejected

        When the task cannot be accepted anymore, Java uses something called: RejectedExecutionHandler

     Q2 : What is RejectedExecutionHandler?
    Answer: RejectedExecutionHandler defines what should happen when a task cannot be accepted by the thread pool.
            Java already provides 4 built-in policies.

            | Policy              | Behaviour                      |
            | ------------------- | ------------------------------ |
            | AbortPolicy         | Throws exception (default)     |
            | CallerRunsPolicy    | Calling thread executes task   |
            | DiscardPolicy       | Silently drops task            |
            | DiscardOldestPolicy | Removes oldest task from queue |


    Incoming Tasks
      ↓
    ThreadPoolExecutor
          ↓
    Core Threads
          ↓
    Queue
          ↓
    Extra Threads
          ↓
    Reject Policy


 */