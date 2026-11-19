```java
public class RateLimiterTokenBucket {

    private final int capacity;
    private final double refillRatePerSecond;
    private double tokens;
    private long lastRefillTimestamp;

    public RateLimiterTokenBucket(int capacity, double refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean allowRequest(int tokensNeeded) {
        refill();
        if (tokens >= tokensNeeded) {
            tokens -= tokensNeeded;
            return true;
        }
        return false;
    }

    public synchronized double getAvailableTokens() {
        refill();
        return tokens;
    }

    private void refill() {
        long now = System.nanoTime();
        double elapsedSeconds = (now - lastRefillTimestamp) / 1_000_000_000.0;
        double addedTokens = elapsedSeconds * refillRatePerSecond;
        if (addedTokens > 0) {
            tokens = Math.min(capacity, tokens + addedTokens);
            lastRefillTimestamp = now;
        }
    }
}
```