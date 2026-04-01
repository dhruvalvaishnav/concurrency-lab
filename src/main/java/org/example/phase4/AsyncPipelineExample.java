package org.example.phase4;

import java.util.concurrent.CompletableFuture;

public class AsyncPipelineExample {
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture.supplyAsync(() -> {
                    System.out.println("Fetching Data...");
                    return 10;
                })
                .thenApply(n -> n * 2)
                .thenApply(n -> n + 5)
                .thenAccept(res -> System.out.println("Final result = " + res));

        System.out.println("Main thread continues...");
        Thread.sleep(2000);
    }
}

/*
    Output:
    Main thread continues...
    Fetching data...
    Final result = 25

    Notice:
    main thread not blocked

    Q1: Why use CompletableFuture instead of Future?

    Answer:

    Future:
    Blocking
    Cannot chain tasks
    Limited functionality

    CompletableFuture:
    Non-blocking async pipeline
    Supports chaining
    Supports combining tasks
    Better error handling

    *** One Important Thing:

        By default:
        CompletableFuture.supplyAsync() -
        uses:
            ForkJoinPool.commonPool() - Which belongs to ForkJoinPool.

        In production systems like Spring Boot, we usually provide custom executors.
        Example:

        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture.supplyAsync(() -> getUser(), executor);

 */