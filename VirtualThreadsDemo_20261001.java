```java
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        runVirtualThreadExample();
    }

    private static void runVirtualThreadExample() {
        Thread thread = Thread.ofVirtual().start(() -> {
            // task
        });
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void runMultipleVirtualThreads() {
        for (int i = 0; i < 10; i++) {
            Thread.ofVirtual().start(() -> {
                // task
            });
        }
   