```java
public class CacheAsidePattern {

    public Object get(String key) {
        // TODO: implement cache lookup and fallback to DB
        return null;
    }

    public void put(String key, Object value) {
        // TODO: implement cache update and DB write
    }

    public void delete(String key) {
        // TODO: implement cache eviction and DB delete
    }

    private Object loadFromDatabase(String key) {
        // TODO: implement DB read
        return null;
    }

    private void writeToDatabase(String key, Object value) {
        // TODO: implement DB write
    }

    private void deleteFromDatabase(String key) {
        // TODO: implement DB delete
    }
}
```