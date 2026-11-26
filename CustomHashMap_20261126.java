```java
import java.util.Objects;

public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (Objects.equals(e.key, key)) {
                V old = e.value;
                e.value = value;
                return old;
            }
        }
        addEntry(hash, key, value, index);
        return null;
    }

    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (Objects.equals(e.key, key)) {
                return e.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        Node<K, V> prev = null;
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (Objects.equals(e.key, key)) {
                if (prev == null) {
