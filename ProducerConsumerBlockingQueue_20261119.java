```java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerBlockingQueue<T> {
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    public void put(T item) throws InterruptedException {
        queue.put(item);
    }

    public T take() throws InterruptedException {
        return queue.take();
    }

    public boolean offer(T item) {
        return queue.offer(item);
    }

    public T poll() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
```