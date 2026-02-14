package org.example.demo;

public class SharedExample {
    public static void main(String[] args) {
        Shared obj = new Shared();

        Thread writer = new Thread(() -> {
            obj.number = 42;
            obj.ready = true;
        });

        Thread reader = new Thread(() -> {
            if (obj.ready) {
                System.out.println(obj.number);
            }
        });

        writer.start();
        reader.start();
    }
}

class Shared {
    // Now ready = true guarantees visibility of number = 42.
    volatile boolean ready = false;
    int number = 0;
}

//Real-World Use Cases
//Flag-based inter-thread signaling (volatile boolean running)
//Double-checked locking for singletons
//Non-blocking algorithms
//Concurrent caches