```java
public class RateLimiterTokenBucket {

    private final long capacity;
    private final long refillRatePerSecond;
    private long tokens;
    private long lastRefillTimestamp;

    public RateLimiterTokenBucket(long capacity, long refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public void acquire() {
        // implementation
    }

    public boolean tryAcquire() {
        // implementation
        return false;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getRefillRatePerSecond() {
        return refillRatePerSecond;
    }

    public long getTokens() {
        return tokens;
    }
}
```