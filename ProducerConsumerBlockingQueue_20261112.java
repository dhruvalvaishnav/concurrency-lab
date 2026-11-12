```java
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerBlockingQueue {
    private final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public void produce() throws InterruptedException {
        // implementation
    }

    public void consume() throws InterruptedException {
        // implementation
    }

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerBlockingQueue pcq = new ProducerConsumerBlockingQueue();
        Thread producer = new Thread(() -> {
            try { pcq.produce(); } catch (InterruptedException e) {}
        });
        Thread consumer = new Thread(() -> {
            try { pcq.consume(); } catch (InterruptedException e) {}
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
```