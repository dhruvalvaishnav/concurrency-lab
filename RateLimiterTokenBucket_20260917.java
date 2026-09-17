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

    public synchronized boolean tryAcquire(long tokens) {
        refill();
        if (availableTokens >= tokens) {
            availableTokens -= tokens;
            return true;
        }
        return false;
    }

    public synchronized void acquire(long tokens) throws InterruptedException {
        while (!tryAcquire(tokens)) {
            Thread.sleep(1);
        }
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsedNanos = now - lastRefillTimestamp;
        long tokensToAdd = (elapsedNanos * refillRatePerSecond) / 1_000_000_000L;
        if (tokensToAdd > 0) {
            availableTokens = Math.min(capacity, availableTokens + tokensToAdd);
            lastRefillTimestamp = now;
        }
    }

    public synchronized long getAvailableTokens() {
        refill();
        return availableTokens;
    }
}
```