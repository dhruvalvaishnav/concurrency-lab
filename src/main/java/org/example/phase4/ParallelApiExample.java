package org.example.phase4;

import java.util.concurrent.CompletableFuture;

public class ParallelApiExample {
    public static void main(String[] args) {

        try {

            // Run API call asynchronously to fetch users
            CompletableFuture<String> users = CompletableFuture.supplyAsync(() -> getUsers());

            // Run API call asynchronously to fetch orders
            CompletableFuture<String> orders = CompletableFuture.supplyAsync(() -> getOrders());

            // Run API call asynchronously to fetch payment info
            CompletableFuture<String> payment = CompletableFuture.supplyAsync(() -> getPayment());

            // All 3 API calls above start running in parallel
            // CompletableFuture.allOf waits until ALL futures complete
            CompletableFuture.allOf(users, orders, payment).join();

            // join() retrieves result (similar to Future.get() but no checked exception)

            // Print results after all tasks complete
            System.out.println(users.join());
            System.out.println(orders.join());
            System.out.println(payment.join());

        } catch (Exception e) {
            // If something fails, interrupt current thread
            Thread.currentThread().interrupt();
        }
    }

    // Simulated API call for Payment service
    private static String getPayment() {
        sleep(2500); // simulate slow API (2.5 seconds)
        return "Payment Data";
    }

    // Simulated API call for Orders service
    private static String getOrders() {
        sleep(1200); // simulate API latency
        return "Order Data";
    }

    // Simulated API call for User service
    private static String getUsers() {
        sleep(1000); // simulate API latency
        return "User Data";
    }

    // Utility method to simulate network delay
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {
            // Ignore interruption for demo purposes
        }
    }

}


/*

    If an interviewer asks why this is powerful in microservices, you can answer:

    Instead of calling APIs sequentially (total time = 1000 + 1200 + 2500 = 4700ms)
    We call them in parallel → total time ≈ max time = 2500ms

    This pattern is heavily used in:
    API Gateway aggregation
    Microservice orchestration
    Parallel database queries

    ---------------------------------------------------------------------------------

    | Method      | Purpose                              |
    | ----------- | ------------------------------------ |
    | supplyAsync | async task returning result          |
    | runAsync    | async task with no result            |
    | thenApply   | transform result                     |
    | thenAccept  | consume result                       |
    | thenCombine | combine two futures                  |
    | allOf       | wait for many tasks                  |
    | join        | get result without checked exception |

 */