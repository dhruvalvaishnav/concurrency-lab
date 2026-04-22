```java
public class CacheAsidePattern<K, V> {

    public V get(K key) {
        return null;
    }

    public void put(K key, V value) {
    }

    public void evict(K key) {
    }

    protected V loadFromDatabase(K key) {
        return null;
    }

    protected void writeToDatabase(K key, V value) {
    }
}
```