```java
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CircuitBreaker {

    private enum State { CLOSED, OPEN, HALF_OPEN }

    private final AtomicReference<State> state = new AtomicReference<>(State.CLOSED);
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private final int failureThreshold;
    private final long openTimeoutMillis;

    public CircuitBreaker(int failureThreshold, long openTimeoutMillis) {
        this.failureThreshold = failureThreshold;
        this.openTimeoutMillis = openTimeoutMillis;
    }

    public <T> T call(Callable<T> callable) throws Exception {
        if (state.get() == State.OPEN) {
            throw new CircuitBreakerOpenException();
        }
        try {
            T result = callable.call();
            onSuccess();
            return result;
        } catch (Exception e) {
            onFailure();
            throw e;
        }
    }

    private void onSuccess() {
        if (state.get() == State.HALF_OPEN) {
            reset();
        }
    }

    private void onFailure() {
        if (state.get() == State.CLOSED && failureCount.incrementAndGet() >= failureThreshold) {
            open();
        } else if (state.get() == State.HALF_OPEN) {
            open();
        }
    }

    private void open() {
        state.set(State.OPEN);
        failureCount.set(0);
        new Thread(() -> {
            try {
                Thread.sleep(openTimeoutMillis);
            } catch (InterruptedException ignored) {}
            halfOpen();
        }).start();
    }

    private void halfOpen() {
        state.set(State.HALF_OPEN);
    }

    private void reset() {
        state.set(State.CLOSED);
        failureCount.set(0);
    }

    public boolean