package org.example.phase1;

public class ThreadCreationDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread started: " + Thread.currentThread().getName());

        Thread worker = new Thread(new OrderProcessor());
        System.out.println("Worker state before start : " + worker.getState()); // NEW

        // JVM :
        // Allocates new OS-level thread
        // Registers it with scheduler
        // Creates new call stack
        // Calls run() internally
        worker.start(); // VERY IMPORTANT: start(), not run()
        Thread.sleep(500);
        System.out.println("Worker state during sleep: " + worker.getState()); // TIMED_WAITING

        System.out.println("Worker state after start : " + worker.getState()); // TIMED_WAITING
        worker.join(); // wait for worker to finish
        System.out.println("Worker state after completion : " + worker.getState()); // TERMINATED

        System.out.println("Main thread finished");
    }
}

class OrderProcessor implements Runnable {

    @Override
    public void run() {
        System.out.println("Worker thread running: " + Thread.currentThread().getName());
        System.out.println("Worker state while running : " + Thread.currentThread().getState()); // RUNNABLE

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Worker thread finished");
        System.out.println("Worker state at finished : " + Thread.currentThread().getState()); // RUNNABLE
    }
}


/**
 * | State         | Meaning                                             |
 * | ------------- | --------------------------------------------------- |
 * | NEW           | Created but not started                             |
 * | RUNNABLE      | Eligible to run (may be running or waiting for CPU) |
 * | BLOCKED       | Waiting to acquire monitor lock                     |
 * | WAITING       | Waiting indefinitely (wait(), join())               |
 * | TIMED_WAITING | Sleeping / timed wait                               |
 * | TERMINATED    | run() finished                                      |
 * <p>
 * <p>
 * <p>
 * 1️⃣ Why does state show RUNNABLE even when thread might not be actively running on CPU?
 * Answer: In Java, the RUNNABLE state means the thread is either actively executing or is ready
 *          to execute but waiting for CPU time. The actual CPU scheduling is handled by the OS, not the JVM.
 *          Therefore, a thread can be in RUNNABLE state even if it is not currently running on the CPU.
 *
 *      🔬 Deep Insight
 *      Java does NOT distinguish between:
 *      Ready
 *      Running
 *      Both are combined into RUNNABLE.
 *
 * <p>
 * 2️⃣ Difference between WAITING and BLOCKED
 * Answer:
 *          BLOCKED
 *          A thread is in BLOCKED state when it is waiting to acquire a monitor lock (entering a synchronized block/method)
 *          but another thread currently holds that lock.
 * <p>
 *         WAITING
 *          A thread is in WAITING state when it is waiting indefinitely for another thread to perform a particular action,
 *          such as calling notify(), notifyAll(), or when join() is used without timeout.
 *
 *  DIFFERENCE:
 *  | BLOCKED                  | WAITING                                      |
 * | ------------------------ | -------------------------------------------- |
 * | Waiting for monitor lock | Waiting for explicit signal                  |
 * | Caused by synchronized   | Caused by wait(), join(), LockSupport.park() |
 * | JVM lock mechanism       | Thread communication mechanism               |
 *
 *
 * 3️⃣ Why sleep() does not release lock but wait() does?
 *  Answer:
 *          sleep() is a static method of Thread and only pauses execution for a specified time.
 *          It does not interact with object monitors, so it does not release any locks.
 *
 *          wait(), on the other hand, is defined in Object class and must be called inside a synchronized block.
 *          When wait() is called, the thread releases the monitor lock and enters WAITING state until another thread calls notify() or notifyAll().
 *
 *          Why This Matters Deeply
 *          If sleep() released locks: You could break mutual exclusion accidentally.
 *
 *          If wait() didn’t release locks:Deadlock would happen immediately in producer-consumer patterns.
 *
 *          Why is wait() in Object class and not Thread?
 *          Because:
 *          Lock belongs to object
 *          Communication happens via shared object monitor
 *          Thread is not the owner of the lock — object is
 *
 */