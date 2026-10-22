```java
public class RateLimiterTokenBucket {

    private final long capacity;
    private final long refillTokens;
    private final long refillPeriodMillis;
    private long availableTokens;
    private long lastRefillTimestamp;

    public RateLimiterTokenBucket(long capacity, long refillTokens, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillPeriodMillis = refillPeriodMillis;
        this.availableTokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public synchronized void acquire() {
        refill();
        while (availableTokens <= 0) {
            try {
                wait(refillPeriodMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            refill();
        }
        availableTokens--;
    }

    public synchronized boolean tryAcquire() {
        refill();
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }
        return false;
    }

    public synchronized long getAvailableTokens() {
        refill();
        return availableTokens;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;
        if (elapsed > 0) {
            long tokensToAdd = (elapsed / refillPeriodMillis) * refillTokens;
            if (tokensToAdd > 0) {
                availableTokens = Math.min(capacity, availableTokens + tokensToAdd);
                lastRefillTimestamp = now;
            }
        }
    }
}
```