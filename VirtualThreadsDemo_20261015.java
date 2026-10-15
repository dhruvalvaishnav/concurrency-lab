```java
public class VirtualThreadsDemo {

    public static void main(String[] args) {
        new VirtualThreadsDemo().runDemo();
    }

    public void runDemo() {
        createVirtualThread(this::task1).start();
        createVirtualThread(this::task2).start();
    }

    private Thread createVirtualThread(Runnable task) {
        return Thread.ofVirtual().unstarted(task);
    }

    private void task1() {
        // task implementation
    }

    private void task2() {
        // task implementation
    }
}
```