```java
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        runDemo();
    }

    public static void runDemo() {
        // Example usage of virtual threads
        createVirtualThread(() -> System.out.println("Hello from virtual thread"));
        runMultipleTasks();
    }

    public static void createVirtualThread(Runnable task) {
        // Create and start a virtual thread
    }

    public static void runMultipleTasks() {
        // Run several tasks concurrently using virtual threads
    }

    public static void runTaskWithTimeout(Runnable task, long timeout, java.util.concurrent.TimeUnit unit) {
        // Run a task with a timeout using virtual threads
    }

    public static void handleExceptions() {
        // Demonstrate exception handling in virtual threads
    }
}
```