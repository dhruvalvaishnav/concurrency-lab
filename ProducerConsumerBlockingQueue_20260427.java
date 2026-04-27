public class ProducerConsumerBlockingQueue<T> {
    public ProducerConsumerBlockingQueue(int capacity) {}

    public void put(T item) throws InterruptedException {}

    public T take() throws InterruptedException {}

    public int size() { return 0; }

    public boolean isEmpty() { return false; }
}