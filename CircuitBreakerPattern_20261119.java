```java
public class CircuitBreakerPattern {

    private enum State { CLOSED, OPEN, HALF_OPEN }

    private State state = State.CLOSED;
    private int failureCount = 0;
    private final int failureThreshold;
    private final long openTimeoutMillis;
    private long lastFailureTime = 0;

    public CircuitBreakerPattern(int failureThreshold, long openTimeoutMillis) {
        this.failureThreshold = failureThreshold;
        this.openTimeoutMillis = openTimeoutMillis;
    }

    public <T> T call(Callable<T> callable) throws Exception {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime >= openTimeoutMillis) {
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

    private void recordSuccess() {
        if (state == State.HALF_OPEN) {
            state = State.CLOSED;
            failureCount = 0;
        }
    }

    private void recordFailure() {
        failureCount++;
        if (failureCount >= failureThreshold) {
            state = State.OPEN;
            lastFailureTime = System.currentTimeMillis();
        }
    }

    public boolean isOpen() {
        return state == State.OPEN