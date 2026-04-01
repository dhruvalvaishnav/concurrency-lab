package org.example.phase4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            int value = 0;
            try {
                while (true) {
                    blockingQueue.put(value);
                    System.out.println("Produced: " + value);
                    value++;
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int taken = blockingQueue.take();
                    System.out.println("Consumed: " + taken);
                    Thread.sleep(1200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}

/*
    BlockingQueue Concept : A BlockingQueue automatically handles:

        Producer waits if queue full
        Consumer waits if queue empty

        Without writing:
        wait()
        notify()
        locks
        conditions

        Example:
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        Producer:
        queue.put(1); // waits if full

        Consumer:
        queue.take(); // waits if empty
 */