package org.example.phase2;

public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> work(), "Thread-1");
        Thread t2 = new Thread(() -> work(), "Thread-2");

        t1.start();
        t2.start();

        //One thread waits, but it never holds a lock while waiting for a lower-order lock.
        //Lock ordering prevents deadlock by eliminating circular wait.
        // Since all threads acquire locks in the same global order, a dependency cycle cannot form, therefore deadlock becomes impossible.
    }

    // Now both threads lock:
    //lockA → lockB
    //No circular wait → No deadlock.
    private static void work() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " acquired lockA");

            sleep(100);

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + " acquired lockB");
            }
        }
    }

    // Why Deadlock Happened:
    // | Thread | Holds | Waiting For |
    // | T1     | lockA | lockB       |
    // | T2     | lockB | lockA       |

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        🧠 The 4 Necessary Conditions For Deadlock (Very Important)

        All must be true:

        Mutual Exclusion — resource cannot be shared
        Hold and Wait — thread holds one lock, waits for another
        No Preemption — lock cannot be taken away
        Circular Wait — cycle exists

        Break any one → deadlock disappears.


        // how to identify the deadlock:
        1. Find Java Process ID (PID)
            jps -l
        2.You will see something like:
            15320 org.example.phase2.DeadlockDemo (Copy the PID of DeadlockDemo.)
            18452 org.jetbrains.idea.Main
        3.Take Thread Dump
            jstack 15320 (Replace with your PID)

        What You Will See (Important Part):
        Found one Java-level deadlock:
        =============================
        "Thread-1":
          waiting to lock monitor 0x000001800919a370 (object 0x0000000089ec6278, a java.lang.Object),
          which is held by "Thread-2"

        "Thread-2":
          waiting to lock monitor 0x00000180091992d0 (object 0x0000000089ec6268, a java.lang.Object),
          which is held by "Thread-1"

          meaning :
          jstack asks JVM:
            “Give me stack trace of every thread and monitors they hold”
            JVM keeps monitor ownership table → so it can detect cycles.
            So JVM itself detects deadlock — not the OS.

        How do you debug a stuck Java application in production?
        You answer:
        First I take a thread dump using jstack or jcmd and check for BLOCKED threads and deadlock cycles in monitor ownership.


Deadlock prevention strategy:

Global lock ordering

Used in:

databases

operating systems

distributed systems

     */
}
