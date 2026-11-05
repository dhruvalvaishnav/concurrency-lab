```java
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolCustom {

    public ThreadPoolCustom(int corePoolSize,
                            int maximumPoolSize,
                            long keepAliveTime,
                            TimeUnit unit,
                            BlockingQueue<Runnable> workQueue) { }

    public void execute(Runnable command) { }

    public Future<?> submit(Runnable task) { return null; }

    public <T> Future<T> submit(Callable<T> task) { return null; }

    public void shutdown() { }

    public List<Runnable> shutdownNow() { return null; }

    public boolean isShutdown() { return false; }

    public boolean