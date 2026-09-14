```java
public class RateLimiterTokenBucket {

    public RateLimiterTokenBucket(long capacity, long refillTokens, long refillPeriodMillis) {
    }

    public void acquire() {
    }

    public boolean tryAcquire() {
        return false;
    }

    public boolean tryAcquire(long timeout, java.util.concurrent.TimeUnit unit) {
        return false;
    }

    public long getAvailableTokens() {
        return 0;
    }

    public long getCapacity() {
        return 0;
    }

    public void setCapacity(long capacity) {
    }

    public void setRefillRate(long tokens, long periodMillis) {
    }

    public void stop() {
    }

    public void start() {
    }
}
```