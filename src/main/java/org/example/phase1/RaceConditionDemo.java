package org.example.phase1;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionDemo {

    // If counter is instance-level:
    // You could use synchronized(this) because each object has its own monitor.
    // However, it is better to use a private lock object to prevent external code from locking on the same object and causing unintended contention.
    private static int counter = 0;

    // Why use a separate lock object instead of class-level lock?
    // Using a dedicated private lock object avoids accidental lock sharing and reduces unnecessary contention.
    // Locking on the class object may block unrelated synchronized static methods, which reduces scalability and increases coupling.
    private static final Object lock = new Object();

    //private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> increment(), "T1");
        Thread t2 = new Thread(() -> increment(), "T2");
        Thread t3 = new Thread(() -> increment(), "T3");
        Thread t4 = new Thread(() -> increment(), "T4");
        Thread t5 = new Thread(() -> increment(), "T5");
        Thread t6 = new Thread(() -> increment(), "T6");
        Thread t7 = new Thread(() -> increment(), "T7");
        Thread t8 = new Thread(() -> increment(), "T8");
        Thread t9 = new Thread(() -> increment(), "T9");
        Thread t10 = new Thread(() -> increment(), "T10");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
        t6.join();
        t7.join();
        t8.join();
        t9.join();
        t10.join();

        System.out.println("Expected: 1000000");
        System.out.println("Actual: " + counter);
    }

    private static void increment() {
        for (int i = 0; i < 100000; i++) {
            // one way

            synchronized (lock) {
                counter++; // this is 3 instruction
                // 1. Read counter from memory
                // 2. Add 1
                // 3. Write back to memory
            }

            // modern way use AtomicInteger
            //counter.getAndIncrement();
        }
    }
}


/**
 * Race condition happens when:
     Shared mutable state exists
     Multiple threads access it
     At least one modifies it
     No synchronization
     All 4 must be true.

     if we make method synchronized :
     It acquires monitor lock on RaceConditionDemo.class
     Other thread trying to enter waits
     Only one thread increments at a time
     Lock released after method ends
     This ensures: Mutual exclusion
        (this is heavy as it locks entire method which reduces concurrency.)

     AtomicInteger uses:

     Compare-And-Swap (CAS)
     CPU-level atomic instruction.

     No blocking.
     No monitor.
     Non-blocking algorithm.

     | Approach      | Blocking? | Performance                 |
     | ------------- | --------- | --------------------------- |
     | synchronized  | Yes       | Slower under contention     |
     | AtomicInteger | No        | Faster under low contention |


     Never lock on:

     String literals
     Class objects
     Public objects
     this (in public classes)
     Unless absolutely required.

     | Lock Type                 | Safe?         | Why                           |
     | ------------------------- | ------------- | ----------------------------- |
     | synchronized(this)        | Sometimes     | Risk of external interference |
     | synchronized(class)       | Risky         | Global lock for class         |
     | private final Object lock | Best practice | Fully encapsulated            |


    Why does AtomicInteger guarantee both: Atomicity,Visibility ??
    Answer:
    compareAndSet(expectedValue, newValue)
    Internally this uses:
    CPU-level CAS (Compare-And-Swap) instruction

     CAS is:
     A single hardware atomic instruction
     Executed without interruption
     Either succeeds or fails as one unit
     So atomicity comes from:
     Hardware-supported atomic instruction

     Not from JVM magic.
     Not from locking.
     Not from combining instructions.

     Inside AtomicInteger:

     private volatile int value; ( The value field is declared as: volatile )
     That means:
     Reads always come from main memory
     Writes are flushed to main memory
     Memory barriers are inserted
     Prevents reordering

     So visibility comes from:
     volatile + memory barriers
     Not from CAS itself.

     AtomicInteger guarantees atomicity using a hardware-level Compare-And-Swap (CAS) instruction,
     which ensures the update occurs atomically without locking.

    It guarantees visibility because the internal value field is declared volatile,
    which ensures that updates are immediately visible to other threads according to the Java Memory Model.

 🧠 What Happens When 100+ Threads Use AtomicInteger?
    Remember how it works internally:
     do {
     oldValue = value;
     newValue = oldValue + 1;
     } while (!compareAndSet(oldValue, newValue));

     This is called a: CAS retry loop (spin loop)
     No locking.
     But also… no waiting.

     🔥 Under Low Contention (Few Threads)
     Example: 2–4 threads
     T1 succeeds CAS
     T2 succeeds CAS
     T3 succeeds CAS
     Almost no retries → Very fast 🚀
     This is why AtomicInteger beats synchronized most of the time.

     💥 Under High Contention (100 Threads)
     Now imagine:
     All 100 threads read value = 500 at same time.
     They all try:
        CAS(500 → 501)
        Only ONE thread wins.
        99 threads FAIL.
        Now all 99 retry again:
        read → compute → CAS → fail → retry
        read → compute → CAS → fail → retry
        read → compute → CAS → fail → retry
    This creates:  CPU spinning storm - The CPU spends time retrying instead of doing useful work.
     📉 Result
     Performance drops dramatically.
     Sometimes worse than synchronized.

    Because synchronized:
         Blocks threads
         OS parks them
         CPU rests
    AtomicInteger:
         Keeps threads active
         Burns CPU cycles

 🧠 This Problem Has a Name : "Contention collapse due to CAS retries"
 Very famous in high-throughput systems.

 🚀 The Solution Java Introduced
 Instead of: AtomicInteger
 Java added: "LongAdder"
 🔥 Why LongAdder Is Faster Under Heavy Load

 AtomicInteger:
     1 shared memory location
     → everyone fights for it
 LongAdder:
     Multiple counters (striped cells)
     → threads update different cells
     → values combined at read


 So instead of 100 threads fighting:
 They spread out.
 Massive scalability improvement.

 If asked: Why can AtomicInteger become slow under heavy contention?
 You answer:  AtomicInteger uses a CAS retry loop.
            Under high contention many threads repeatedly fail the CAS operation and retry, causing excessive CPU spinning.
            This leads to performance degradation. LongAdder solves this by reducing contention using striped counters.

 | Tool          | Strategy         | Best Case            |
 | ------------- | ---------------- | -------------------- |
 | synchronized  | Blocking         | High contention      |
 | AtomicInteger | CAS spinning     | Low contention       |
 | LongAdder     | Striped counters | Very high contention |

 */