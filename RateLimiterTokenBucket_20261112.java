```java
public class RateLimiterTokenBucket {

    private final long capacity;
    private final long refillTokens;
    private final long refillPeriodMillis;
    private long tokens;
    private long lastRefillTimestamp;

    public RateLimiterTokenBucket(long capacity, long refillTokens, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodMillis = refillPeriodMillis;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized boolean tryAcquire() {
        refill();
        if (tokens > 0) {
            tokens--;
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
        return tokens;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;
        if (elapsed > 0) {
            long tokensToAdd = (elapsed / refillPeriodMillis) * refillTokens;
            if (tokensToAdd > 0) {
                tokens = Math.min(capacity, tokens + tokensToAdd);
                lastRefillTimestamp = now;
            }
        }
    }
}
```