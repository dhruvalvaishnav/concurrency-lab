package org.example.phase4;

import java.util.concurrent.CompletableFuture;

public class CombineFuturesExample {
    public static void main(String[] args) {

        CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> 100);

        CompletableFuture<Integer> tax = CompletableFuture.supplyAsync(() -> 20);

        CompletableFuture<Integer> total = price.thenCombine(tax, (p, t) -> p + t);

        System.out.println("Total price = " + total.join());
    }
}
