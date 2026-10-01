```java
public class CacheAsidePattern<K, V> {

    private final Cache<K, V> cache;
    private final DataSource<K, V> dataSource;

    public CacheAsidePattern(Cache<K, V> cache, DataSource<K, V> dataSource) {
        this.cache = cache;
        this.dataSource = dataSource;
    }

    public V get(K key) {
        V value = cache.get(key);
        if (value == null) {
            value = dataSource.load(key);
            if (value != null) {
                cache.put(key, value);
            }
        }
        return value;
    }

    public void put(K key, V value) {
        cache.put(key, value);
        dataSource.save(key, value);
    }

    public void delete(K key) {
        cache.remove(key);
        dataSource.delete(key);
    }

    public void refresh(K key) {
        V value = dataSource.load(key);
        if (value != null) {
            cache.put(key, value);
        } else {
            cache.remove(key);
        }
    }
}
```