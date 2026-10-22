```java
public class CircuitBreaker {

    public enum State {
        CLOSED,
        OPEN,
        HALF_OPEN
    }

    private State state = State.CLOSED;
    private int failureCount = 0;
    private int successCount = 0;
    private final int failureThreshold;
    private final int successThreshold;
    private final long timeoutMillis;
    private long lastFailureTime = 0;

    public CircuitBreaker(int failureThreshold, int successThreshold, long timeoutMillis) {
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
        this.timeoutMillis = timeoutMillis;
    }

    public boolean allowRequest() {
        switch (state) {
            case CLOSED:
                return true;
            case OPEN:
                if (System.currentTimeMillis() - lastFailureTime >= timeoutMillis) {
                    state = State.HALF_OPEN;
                    return true;
                }
                return false;
            case HALF_OPEN:
                return true;
            default:
                return false;
        }
    }

    public void recordSuccess() {
        if (state == State.HALF_OPEN) {
            successCount++;
            if (successCount >= successThreshold) {
                reset();
            }
        }
    }

    public void recordFailure() {
        if (state == State.CLOSED) {
            failureCount++;
            if (failureCount >= failureThreshold) {
                trip();
            }
        } else if (state == State.HALF_OPEN) {
            trip();
        }
    }

    public State getState() {
        return state;
    }

    private void trip() {
        state = State.OPEN;
        lastFailureTime = System.currentTimeMillis();
        failureCount = 0