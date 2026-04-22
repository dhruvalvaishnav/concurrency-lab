```java
package resilience;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CircuitBreaker<T> {

    private enum State { CLOSED, OPEN, HALF_OPEN }

    private final AtomicReference<State> state = new AtomicReference<>(State.CLOSED);
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private final int failureThreshold;
    private final long openTimeoutMillis;
    private volatile long openTimestamp;

    public CircuitBreaker(int failureThreshold, long openTimeoutMillis) {
        this.failureThreshold = failureThreshold;
        this.openTimeoutMillis = openTimeoutMillis;
    }

    public T call(Callable<T> callable) throws Exception {
        if (state.get() == State.OPEN) {
            if (System.currentTimeMillis() - openTimestamp > openTimeoutMillis) {
                state.set(State.HALF_OPEN);
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
        if (state.get() == State.HALF_OPEN) {
            state.set(State.CLOSED);
            failureCount.set(0);
        }
    }

    private void recordFailure() {
        int failures = failureCount.incrementAndGet();
        if (failures >= failureThreshold) {
            state.set(State.OPEN