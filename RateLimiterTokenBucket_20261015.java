```java
public class RateLimiterTokenBucket {

    public RateLimiterTokenBucket(long capacity, long refillRate, long refillIntervalMillis) {
    }

    public void acquire() {
    }

    public boolean tryAcquire() {
        return false;
    }

    public long getTokens() {
        return 0;
    }

    public long getCapacity() {
        return 0;
    }

    public long getRefillRate() {
        return 0;
    }

    public long getRefillIntervalMillis() {
        return 0;
    }
}
```