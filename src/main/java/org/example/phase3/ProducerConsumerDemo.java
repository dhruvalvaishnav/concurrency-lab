package org.example.phase3;

/**
 * Basic producer-consumer with wait() / notify()
 */
class SharedBuffer {
    private int data;
    private boolean hasData = false;

    // Producer puts data
    public synchronized void produce(int value) throws InterruptedException {
        // If buffer already has data, wait
        while (hasData) {
            wait(); // releases lock and sleeps until notified
        }

        data = value;
        hasData = true;
        System.out.println("Produced: " + data);

        // Notify consumer that data is available
        notify(); // wakes up one waiting thread
    }

    // Consumer takes data
    public synchronized int consume() throws InterruptedException {
        // If buffer is empty, wait
        while (!hasData) {
            wait(); // releases lock and sleeps until notified
        }

        int value = data;
        hasData = false;
        System.out.println("Consumed: " + value);

        // Notify producer that buffer is empty
        notify();
        return value;
    }

}

public class ProducerConsumerDemo {
    public static void main(String[] args) {

        SharedBuffer buffer = new SharedBuffer();

        // Producer thread
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(100); // simulate production time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "PRODUCER");

        // Consumer thread
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.consume();
                    Thread.sleep(150); // simulate consumption time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CONSUMER");

        producer.start();
        consumer.start();

    }
}
