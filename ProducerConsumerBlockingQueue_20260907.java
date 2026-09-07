```java
public class ProducerConsumerBlockingQueue<T> {
    public ProducerConsumerBlockingQueue(int capacity) { }

    public void enqueue(T item) throws InterruptedException { }

    public T dequeue() throws InterruptedException { }

    public int size() { return 0; }

    public boolean isEmpty() { return false; }
}
```