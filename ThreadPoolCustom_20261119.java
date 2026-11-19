```java
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPoolCustom {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] workers;
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public ThreadPoolCustom(int poolSize) {
        taskQueue = new LinkedBlockingQueue<>();
        workers = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Thread(new Worker());
            workers[i].start();
        }
    }

    public void submit(Runnable task) {
        if (!isShutdown.get()) {
            taskQueue.offer(task);
        }
    }

    public void shutdown() {
        isShutdown.set(true);
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!isShutdown.get() || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
```