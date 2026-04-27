```java
public class CircuitBreaker<T> {

    public CircuitBreaker(int failureThreshold, long timeoutMillis) {}

    public T call(Callable<T> callable) throws Exception {}

    public void open() {}

    public void close() {}

    public void halfOpen() {}

    public boolean isOpen() { return false; }

    public boolean isClosed() { return false; }

    public boolean isHalfOpen() { return false; }

    public void recordSuccess() {}

    public void recordFailure() {}

    public void reset() {}
}
```