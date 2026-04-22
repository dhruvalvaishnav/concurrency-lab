```java
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        runTasks();
    }

    private static void runTasks() {
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executor.submit(() -> processTask(taskId));
        }
        executor.shutdown();
    }

    private static void processTask(int id) {
        // task logic
    }
}
```