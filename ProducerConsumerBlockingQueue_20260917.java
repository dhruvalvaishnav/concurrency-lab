public class ProducerConsumerBlockingQueue<T> {
    public ProducerConsumerBlockingQueue(int capacity) {}

    public void produce(T item) throws InterruptedException {}

    public T consume() throws InterruptedException { return null; }

    public int size() { return 0; }
}