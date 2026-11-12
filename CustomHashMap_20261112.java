```java
public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private int threshold;

    public CustomHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public CustomHashMap(int initialCapacity) {
        this.table = new Node[initialCapacity];
        this.threshold = (int)(initialCapacity * LOAD_FACTOR);
    }

    public V put(K key, V value) {
        return null;
    }

    public V get(K key) {
        return null;
    }

    public V remove(K key) {
        return null;
    }

    public boolean containsKey(K key) {
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
    }

    private void resize() {
    }

    private int hash(K key) {
        return 0;
    }

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
```