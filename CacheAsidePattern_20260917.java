```java
public class CacheAsidePattern<K, V> {

    public V get(K key) {
        // TODO: implement cache lookup and fallback to DB
        return null;
    }

    public void put(K key, V value) {
        // TODO: implement cache update and DB write
    }

    public void delete(K key) {
        // TODO: implement cache eviction and DB delete
    }

    private V loadFromDB(K key) {
        // TODO: implement DB read
        return null;
    }

    private void writeToDB(K key, V value) {
        // TODO: implement DB write
    }

    private void deleteFromDB(K key) {
        // TODO: implement DB delete
    }
}
```