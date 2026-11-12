```java
public class CircuitBreaker {

    public CircuitBreaker(int failureThreshold, long timeout) {
    }

    public boolean allowRequest() {
        return false;
    }

    public void recordSuccess() {
    }

    public void recordFailure() {
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

    public void reset() {
    }
}
```