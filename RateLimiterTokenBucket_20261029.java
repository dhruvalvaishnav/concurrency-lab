```java
public class RateLimiterTokenBucket {

    private final long capacity;
    private final long refillTokens;
    private final long refillPeriodNanos;
    private long availableTokens;
    private long lastRefillTimestamp;

    public RateLimiterTokenBucket(long capacity, long refillTokens, long refillPeriodNanos) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodNanos = refillPeriodNanos;
        this.availableTokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean tryAcquire() {
        refill();
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }
        return false;
    }

    public synchronized void acquire() throws InterruptedException {
        while (!tryAcquire()) {
            Thread.sleep(1);
        }
    }

    public synchronized long getAvailableTokens() {
        refill();
        return availableTokens;
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsed = now - lastRefillTimestamp;
        if (elapsed > 0) {
            long tokensToAdd = (elapsed / refillPeriodNanos) * refillTokens;
            if (tokensToAdd > 0) {
                availableTokens = Math.min(capacity, availableTokens + tokensToAdd);
                lastRefillTimestamp = now;
            }
        }
    }
}
```