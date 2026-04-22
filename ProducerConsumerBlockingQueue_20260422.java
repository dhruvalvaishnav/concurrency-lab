```java
public class ProducerConsumerBlockingQueue<E> {
    public ProducerConsumerBlockingQueue(int capacity) { }

    public void put(E item) throws InterruptedException { }

    public E take() throws InterruptedException { return null; }

    public int size() { return 0; }

    public boolean isEmpty() { return false; }

    public boolean isFull() { return false; }
}
```