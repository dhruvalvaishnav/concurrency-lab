```java
public class CacheAsidePattern {

    public Object get(String key) {
        return null;
    }

    public void put(String key, Object value) {
    }

    public void delete(String key) {
    }

    private Object loadFromDatabase(String key) {
        return null;
    }

    private void writeToDatabase(String key, Object value) {
    }

    private void removeFromDatabase(String key) {
    }
}
```