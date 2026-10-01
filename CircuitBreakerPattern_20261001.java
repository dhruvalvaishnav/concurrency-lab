```java
public class CircuitBreaker {

    public CircuitBreaker(int failureThreshold, long timeout, int successThreshold) {
    }

    public boolean isOpen() {
        return false;
    }

    public boolean isClosed() {
        return false;
    }

    public boolean isHalfOpen() {
        return false;
    }

    public <T> T call(Callable<T> callable) throws Exception {
        return null;
    }

    public void recordSuccess() {
    }

    public void recordFailure() {
    }

    public void reset() {
    }
}
```