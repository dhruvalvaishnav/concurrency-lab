```java
public class CircuitBreaker {

    public enum State {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    private State state;
    private int failureThreshold;
    private long timeout;
    private long lastFailureTime;

    public CircuitBreaker(int failureThreshold, long timeout) {
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
        this.state = State.CLOSED;
    }

    public <T> T call(Callable<T> callable) throws Exception {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime > timeout) {
                state = State.HALF_OPEN;
            } else {
                throw new CircuitBreakerOpenException();
            }
        }

        try {
            T result = callable.call();
            if (state == State.HALF_OPEN) {
                state = State.CLOSED;
            }
            return result;
        } catch (Exception e) {
            recordFailure();
            throw e;
        }
    }

    private void recordFailure() {
        if (state == State.CLOSED) {
            failureThreshold--;
            if (failureThreshold <= 0) {
                state = State.OPEN;
                lastFailureTime = System.currentTimeMillis();
            }
        } else if (state == State.HALF_OPEN) {
            state = State.OPEN;
            lastFailureTime = System.currentTimeMillis();
        }
    }

    public State getState() {
        return state;
    }

    public void reset() {
        state = State.CLOSED;
        failureThreshold = 0;
    }

    public static class CircuitBreakerOpenException extends RuntimeException {
        public CircuitBreakerOpenException() {
            super("Circuit breaker is open");
        }
    }
}
```