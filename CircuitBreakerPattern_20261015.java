```java
public class CircuitBreaker {

    public enum State { CLOSED, OPEN, HALF_OPEN }

    private State state = State.CLOSED;
    private int failureCount = 0;
    private final int failureThreshold;
    private final long openTimeoutMillis;
    private long openTimestamp;

    public CircuitBreaker(int failureThreshold, long openTimeoutMillis) {
        this.failureThreshold = failureThreshold;
        this.openTimeoutMillis = openTimeoutMillis;
    }

    public <T> T call(Callable<T> callable) throws Exception {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - openTimestamp >= openTimeoutMillis) {
                state = State.HALF_OPEN;
            } else {
                throw new CircuitBreakerOpenException();
            }
        }
        try {
            T result = callable.call();
            recordSuccess();
            return result;
        } catch (Exception e) {
            recordFailure();
            throw e;
        }
    }

    public void recordSuccess() {
        if (state == State.HALF_OPEN) {
            close();
        }
    }

    public void recordFailure() {
        failureCount++;
        if (failureCount >= failureThreshold) {
            open();
        }
    }

    public void open() {
        state = State.OPEN;
        openTimestamp = System.currentTimeMillis();
    }

    public void close() {
        state = State.CLOSED;
        failureCount = 0;
    }

    public State getState() {
        return state;
    }

    public static class CircuitBreaker