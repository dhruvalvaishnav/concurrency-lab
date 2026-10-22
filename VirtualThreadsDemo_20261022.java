```java
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        runDemo();
    }

    private static void runDemo() {
        // Example usage of virtual threads
        var thread = createVirtualThread(() -> {
            // Task logic
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static Thread createVirtualThread(Runnable task) {
        return Thread.ofVirtual().unstarted(task);
    }

    private static void runTask(Runnable task) {
        Thread.ofVirtual().start(task);
    }

    private static void runTasksSequentially(Runnable... tasks) {
        for (Runnable task : tasks) {
            runTask(task);
        }
    }

    private static void runTasksInParallel(Runnable... tasks) {
        for (Runnable task : tasks) {
            createVirtualThread(task).start();
        }
    }
}
```