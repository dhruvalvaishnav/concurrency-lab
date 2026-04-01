package org.example.phase3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AdvancedBlockingQueue<T> {

    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final ReentrantLock lock = new ReentrantLock();

    // notFull → for producers
    private final Condition notFull = lock.newCondition();
    // notEmpty → for consumers
    private final Condition notEmpty = lock.newCondition();

    public AdvancedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            //  Always recheck condition after waking → still protects against spurious wakeups
            while (queue.size() == capacity) {
                notFull.await(); // wait until space available
            }
            queue.add(item);
            notEmpty.signal(); // signal a waiting consumer
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await(); // wait until item available
            }
            T item = queue.remove();
            notFull.signal(); // signal a waiting producer
            return item;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<Integer>(10);
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

        // testing purpose
        //testArrayBlockingQueue(arrayBlockingQueue);

        // real world food order processing system
        foodOrderProcessing(queue);

    }

    private static final String POISON_PILL = "STOP";

    private static void foodOrderProcessing(ArrayBlockingQueue<String> orderQueue) {

        // Chef - producer
        Runnable chef = () -> {
            String[] orders = new String[]{"Pizza", "Pav Bhaji", "Manchurian",
                    "Chole Kulcha", "Paneer Bhurji", "Naan", "Burger",
                    "Pasta", "Sandwich", "Fries", "Taco", "Wrap", "Noodles"};

            try {
                for (String order : orders) {
                    orderQueue.put(order);
                    System.out.println("Chef prepared: " + order);
                    Thread.sleep(500);
                }

                // after all order completes, send shutdown signal
                // Important Interview Detail (Multiple Consumers) : If you have N consumers, you must insert N poison pills.
                orderQueue.put(POISON_PILL);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Delivery Person (Consumer)
        Runnable deliveryBoy = () -> {
            try {
                while (true) {

                    String order = orderQueue.take();

                    if (POISON_PILL.equals(order)) {
                        System.out.println("No more orders. Delivery closing.");
                        break;
                    }

                    System.out.println("Delivered order: " + order);
                    Thread.sleep(1200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread chefThread = new Thread(chef, "Chef");
        Thread deliveryBoyThread = new Thread(deliveryBoy, "DeliveryBoy");

        chefThread.start();
        deliveryBoyThread.start();
    }

    private static void testArrayBlockingQueue(ArrayBlockingQueue<Integer> arrayBlockingQueue) {
        // Producer
        Runnable producer = () -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    arrayBlockingQueue.put(i);
                    System.out.println("Item put into arrayBlockingQueue is : " + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // consumer
        Runnable consumer = () -> {
            for (int i = 1; i <= 20; i++) {
                try {
                    int taken = arrayBlockingQueue.take();
                    System.out.println("Item taken from arrayBlockingQueue is : " + taken);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }

}
