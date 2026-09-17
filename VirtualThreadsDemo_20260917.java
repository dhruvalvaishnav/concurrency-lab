```java
import java.util.concurrent.*;

public class VirtualThreadsDemo {

    public static void main(String[] args) {
        runDemo();
    }

    private static void runDemo() {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                // task logic
            });
        }
        executor.shutdown();
    }

    private static void createVirtualThread() {
        Thread.startVirtualThread(() -> {
            // task logic
        });
    }
}
```