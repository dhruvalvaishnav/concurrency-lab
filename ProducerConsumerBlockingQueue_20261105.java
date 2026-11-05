```java
public class ProducerConsumerBlockingQueue<E> {
    private final java.util.concurrent.BlockingQueue<E> queue;

    public ProducerConsumerBlockingQueue(int capacity) {
        queue = new java.util.concurrent.ArrayBlockingQueue<>(capacity);
    }

    public void put(E item) throws InterruptedException {
        queue.put(item);
    }

    public E take() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
```