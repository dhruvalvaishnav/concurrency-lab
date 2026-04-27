```java
public class RateLimiterTokenBucket {

    private final long capacity;
    private final long refillRatePerSecond;
    private long availableTokens;
    private long lastRefillTimestamp;

    public RateLimiterTokenBucket(long capacity, long refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.availableTokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized void acquire(long tokens) {
        refill();
        while (availableTokens < tokens) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            refill();
        }
        availableTokens -= tokens;
    }

    public synchronized boolean tryAcquire(long tokens) {
        refill();
        if (availableTokens >= tokens) {
            availableTokens -= tokens;
            return true;
        }
        return false;
    }

    public synchronized long getAvailableTokens() {
        refill();
        return availableTokens;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getRefillRatePerSecond() {
        return refillRatePerSecond;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedNanos = now - lastRefillTimestamp;
        long tokens