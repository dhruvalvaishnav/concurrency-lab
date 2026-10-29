```java
public class CircuitBreaker {

    public enum State {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    public CircuitBreaker(int failureThreshold, long timeoutMillis) {
    }

    public boolean allowRequest() {
        return false;
    }

    public void recordSuccess() {
    }

    public void recordFailure() {
    }

    public State getState() {
        return null;
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