package org.example.phase1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Example : Bank transfer between two accounts
 * If two threads transfer money opposite directions → deadlock risk.
 * <p>
 * Problem Scenario :
 * Thread 1 → transfer(A → B)
 * Thread 2 → transfer(B → A)
 * <p>
 * If we use synchronized:
 * T1 locks A → waits B
 * T2 locks B → waits A
 * 💥 Deadlock
 * <p>
 * We cannot enforce global ordering because accounts are dynamic.
 * So we use:
 * <p>
 * ReentrantLock.tryLock() with timeout
 * This is called deadlock avoidance (not prevention).
 */
public class TryLockDeadlockPrevention {

    static class Account {
        private final String name;
        private int balance;

        // by default ReentrantLock is NON-FAIR LOCK - meaning Whoever asks at the right moment gets the lock — not who waited longest.
        //private final ReentrantLock lock = new ReentrantLock();

        //true = FAIR LOCK (FIFO queue)
        private final ReentrantLock lock = new ReentrantLock(true);

        // What is tradeoff between fair and non-fair locks?
        // Fair lock prevents starvation by granting access in request order but reduces throughput due to context switching overhead.
        // Non-fair lock improves performance but may cause starvation.

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public boolean withdraw(int amount) {
            if (balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public ReentrantLock getLock() {
            return lock;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }
    }

    public static void transfer(Account from, Account to, int amount) {

        while (true) {

            boolean fromLock = false;
            boolean toLock = false;

            // How can you prevent deadlock when lock ordering isn’t possible?
            // Use timed tryLock and retry mechanism. If a thread cannot acquire all required locks,
            // it releases acquired locks and retries later, breaking the hold-and-wait condition.
            try {
                fromLock = from.getLock().tryLock(1, TimeUnit.MILLISECONDS);
                toLock = to.getLock().tryLock(1, TimeUnit.MILLISECONDS);

                if (fromLock && toLock) {
                    if (from.withdraw(amount)) {
                        to.deposit(amount);
                        System.out.println(Thread.currentThread().getName()
                                + " transferred " + amount + " from "
                                + from.getName() + " to " + to.getName());
                    }
                    return;
                }

            } catch (InterruptedException ignored) {
            } finally {

                if (fromLock) from.getLock().unlock();
                if (toLock) to.getLock().unlock();
            }

            // retry after small delay
            sleep(10);
            System.out.println("retrying...");
        }
    }

    public static void main(String[] args) {

        Account acc1 = new Account("Account-A", 1000);
        Account acc2 = new Account("Account-B", 1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20; i++)
                transfer(acc1, acc2, 10);
        }, "T1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 20; i++)
                transfer(acc2, acc1, 10);
        }, "T2");

        t1.start();
        t2.start();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    /*
        Liveness guarantee
        synchronized → guarantees mutual exclusion only
        tryLock → guarantees progress of system

        We prefer system progress over perfect locking.

        internally:
            Thread-1 got A
            Thread-2 got B

            Thread-1 tried B -> FAILED -> released A
            Thread-2 tried A -> SUCCESS -> finished
            Thread-1 retried -> SUCCESS


            Why is tryLock better than synchronized in dynamic systems?

            synchronized only provides mutual exclusion but may cause permanent blocking and deadlocks because threads wait indefinitely.

            ReentrantLock.tryLock provides timed and non-blocking lock acquisition,
            allowing threads to back off, retry, and maintain system liveness.

            Therefore, it prevents deadlocks and improves fault tolerance in concurrent resource access.

            There are 4 types of lock strategies:
            | Strategy             | Behavior               | Real World Use             |
            | -------------------- | ---------------------- | -------------------------- |
            | synchronized         | blocking forever       | simple critical sections   |
            | ReentrantLock.lock() | interruptible blocking | controlled locking         |
            | tryLock()            | deadlock safe          | distributed systems        |
            | tryLock(timeout)     | production safe        | databases, Kafka consumers |


     */
}
