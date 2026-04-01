package org.example.phase4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<Integer>> futures = new ArrayList<>();

        try {
            for (int i = 1; i <= 10; i++) {
                int num = i;
                // callable example
                Future<Integer> future = executor.submit(() -> {
                    Thread.sleep(500);
                    System.out.println("Processing " + num + " by " +
                            Thread.currentThread().getName());
                    return num * 10;
                });
                futures.add(future);
            }

            /*
                submit 10 tasks
                ↓
                thread pool executes them in parallel
                ↓
                collect results later
             */
            for (Future<Integer> future : futures) {
                System.out.println("Result = " + future.get());
            }

            // blocks the current thread which is main until get the future result - Blocking call
            //System.out.println(future.get());

            // Prevents infinite blocking - blocks the current thread which is main until 2 second to get the future result
            //System.out.println(future.get(2, TimeUnit.SECONDS));

            // future task completed or not
            // System.out.println(future.isDone());

            // Attempts to cancel task
            // System.out.println(future.cancel(false));


            executor.shutdown();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
    Problem With Future:
    Future is useful but has big limitations.
    You cannot chain tasks easily.

    Example scenario:

        API Call
           ↓
        Process Result
           ↓
        Store in DB

    With Future, this becomes messy.

    You must block:
        future.get()
        Blocking = bad for scalable systems.

    ----------------------------------------------------


 */