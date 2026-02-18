package org.example.phase3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Multiple Producers + Multiple Consumers + Bounded Buffer
 * Always use while + notifyAll() in condition-based waiting
 * <p>
 * You just built a BlockingQueue from scratch 🎯
 */
class BoundedBuffer {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int value) throws InterruptedException {

        // We use while instead of if because after a thread wakes up from wait(),
        // the condition must be rechecked.
        // This protects against spurious wakeups and multiple threads waking simultaneously.

        // WAIT UNTIL SPACE AVAILABLE IN QUEUE
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(value);
        System.out.println(Thread.currentThread().getName() +
                " produced " + value + " | size=" + queue.size());

        notifyAll(); // important
    }

    public synchronized int consume() throws InterruptedException {

        // WAIT UNTIL DATA AVAILABLE
        while (queue.isEmpty()) {
            wait();
        }

        int value = queue.poll();

        System.out.println(Thread.currentThread().getName() +
                " consumed " + value + " | size=" + queue.size());

        notifyAll(); // important
        return value;
    }
}

public class MultiProducerConsumerDemo {

    public static void main(String[] args) {

        BoundedBuffer buffer = new BoundedBuffer(3);

        // PRODUCERS
        Runnable producerTask = () -> {
            int i = 1;
            while (true) {
                try {
                    buffer.produce(i++);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // CONSUMERS
        Runnable consumerTask = () -> {
            while (true) {
                try {
                    buffer.consume();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(producerTask, "Producer-1").start();
        new Thread(producerTask, "Producer-2").start();

        new Thread(consumerTask, "Consumer-1").start();
        new Thread(consumerTask, "Consumer-2").start();
    }


    /*
        When using wait/notify:

        1. Always use while
        2. Prefer notifyAll over notify
        3. Condition must be inside synchronized block

        1️⃣ Why while and NOT if around wait()?
        🔥 The Real Reason: Spurious Wakeups + Wrong Thread Wakeups

            A thread waiting on wait() can wake up:
            Because someone called notifyAll()
            Because someone called notify()
            OR for no reason at all (spurious wakeup — allowed by JVM spec)

            Now imagine this:

            Buffer capacity = 3
            Buffer currently full.
            Two producers are waiting.
            A consumer consumes 1 item → calls notifyAll().
            Both producers wake up.
            But only ONE producer should produce.

            If you wrote:
            if (queue.size() == capacity) {
                wait();
            }
            Then:
            Both producers wake up → skip the if check (already passed) → both add → buffer becomes size 4 ❌

            But with:
            while (queue.size() == capacity) {
                wait();
            }

            After waking:
            Thread re-checks condition.
            If still full → goes back to waiting.
            ✔ Safe.


         2️⃣ Why notifyAll() and not notify()?
         🔥 The Real Problem With notify()

            Suppose:

            Buffer is empty.
            2 consumers waiting.
            2 producers waiting (because previously full).
            Producer produces 1 item.

            If it calls:
            notify();
            It might wake up: ❌ Another producer (wrong type) Instead of consumer

            Now:
            Producer wakes → sees buffer not full → produces → buffer fills again
            Consumers still waiting
            System eventually freezes

            This is called: Missed signal / lost notification bug

            Why notifyAll() fixes it
            notifyAll() wakes:
            All producers
            All consumers

            Then:   Each thread rechecks condition using while.
                    Only the correct type proceeds.
                    Others go back to waiting.

     */
}
