package org.example.phase4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // supplyAsync() = runs task in thread pool = No blocking
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
                .thenApply(n -> n * 2) // 20
                .thenApply(n -> n + 1) // 21
                .thenApply(n -> n * 10) // 210
                .thenApply(n -> n);
        System.out.println(future.get()); // 210

    }
}

/*
    CompletableFuture (Modern Async Java):

    Java 8 introduced: CompletableFuture

    This is a non-blocking async pipeline framework.

    Key idea:
    Run async tasks
    then chain operations
    without blocking threads


 */