```java
import java.util.*;

public class CustomHashMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.table = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
        this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * loadFactor);
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.table = (Node<K, V>[]) new Node[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && Objects.equals(e.key, key)) {
                V old = e.value;
                e.value = value;
                return old;
            }
        }
        addEntry(hash, key, value, index);
        return null;
    }

    public V get(Object key