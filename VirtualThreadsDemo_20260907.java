```java
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        Runnable task = () -> System.out.println("Hello from virtual thread");
        Thread thread = Thread.ofVirtual().start(task);
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void runTask(String name) {
        Runnable task = () -> System.out.println("Task " + name);
        Thread.ofVirtual().start(task);
    }

    private static void createVirtualThread(Runnable task) {
        Thread.ofVirtual().start(task);
    }
}
```