```java
public class CacheAsidePattern<K, V> {

    public V get(K key) {
        return null;
    }

    public void put(K key, V value) {
    }

    public void delete(K key) {
    }

    protected V loadFromDB(K key) {
        return null;
    }

    public void refreshCache(K key) {
    }
}
```