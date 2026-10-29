```java
public class CacheAsidePattern<K, V> {

    public V get(K key) {
        return null;
    }

    public void put(K key, V value) {
    }

    public void evict(K key) {
    }

    protected V loadFromDB(K key) {
        return null;
    }

    protected void writeToDB(K key, V value) {
    }
}
```