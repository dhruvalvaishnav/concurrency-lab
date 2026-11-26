```java
public class CircuitBreaker {

    private enum State { CLOSED, OPEN, HALF_OPEN }

    private State state = State.CLOSED;
    private int failureCount = 0;
    private int successCount = 0;
    private final int failureThreshold;
    private final int successThreshold;
    private final long timeoutMillis;
    private long lastFailureTime = 0;

    public CircuitBreaker(int failureThreshold, int successThreshold, long