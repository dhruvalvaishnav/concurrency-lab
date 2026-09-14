```java
public class ThreadPoolCustom {

    public ThreadPoolCustom(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
    }

    public void submit(Runnable task) {
    }

    public void submit(Callable<?> task) {
    }

    public void shutdown() {
    }

    public List<Runnable> shutdownNow() {
        return null;
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void setThreadFactory(ThreadFactory factory) {
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
    }

    public int getPoolSize() {
        return 0;
    }

    public int getActiveCount() {
        return 0;
    }

    public int getQueueSize() {
        return 0;
    }
}
```