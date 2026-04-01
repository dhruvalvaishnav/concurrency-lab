package org.example.phase4;

import java.util.concurrent.CompletableFuture;

public class DashboardService {

    public static void main(String[] args) {

        // Record start time to measure total execution time
        //long startTime = System.currentTimeMillis();
        //System.out.println("start time: " + startTime);

        // Call 3 microservices asynchronously (parallel execution)
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> getUser());
        CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> getOrders());
        CompletableFuture<String> paymentFuture = CompletableFuture.supplyAsync(() -> getPayments());

        // Combine results once futures start completing
        CompletableFuture<DashboardResponse> dashboardFuture =
                userFuture.thenCombine(orderFuture, (user, orders) -> {

                    // Combine results from user and order services
                    DashboardResponse response = new DashboardResponse();
                    response.user = user;
                    response.orders = orders;

                    return response;

                }).thenCombine(paymentFuture, (response, payment) -> {

                    // Add payment service data to existing response
                    response.payment = payment;

                    return response;
                });

        // Wait for all async computations to finish and get final result
        DashboardResponse result = dashboardFuture.join();

        // Print aggregated response
        System.out.println(result);

        // Calculate and print total execution time
        //System.out.println("total time: " + (System.currentTimeMillis() - startTime));
    }

    // Simulated User Service API
    static String getUser() {
        sleep(1000); // simulate network delay (1 second)
        return "User Info";
    }

    // Simulated Order Service API
    static String getOrders() {
        sleep(1500); // simulate network delay (1.5 seconds)
        return "Orders Info";
    }

    // Simulated Payment Service API
    static String getPayments() {
        sleep(2000); // simulate network delay (2 seconds)
        return "Payment Info";
    }

    // Utility method to simulate latency
    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ignored) {
            // ignore interruption for demo
        }
    }
}


// DTO to store aggregated response from multiple services
class DashboardResponse {

    String user;
    String orders;
    String payment;

    @Override
    public String toString() {
        return "DashboardResponse{" +
                "user='" + user + '\'' +
                ", orders='" + orders + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }
}
/*
    | API         | Time |
    | ----------- | ---- |
    | User API    | 1s   |
    | Order API   | 1.5s |
    | Payment API | 2s   |

 */