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

    public void evict(K key) {
        cache.remove(key);
        dataSource.delete(key);
    }

    public void clear() {
        cache.clear();
        dataSource.clear();
    }
}

interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    void clear();
}

interface DataSource<K, V> {
    V load(K key);
    void save(K key, V value);
    void delete(K key);
    void