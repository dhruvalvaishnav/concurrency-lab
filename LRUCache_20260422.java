```java
import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> map;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    public int get(int key) {
        throw new UnsupportedOperationException();
    }

    public void put(int key, int value) {
        throw new UnsupportedOperationException();
    }

    private void addFirst(Node node) {
        throw new UnsupportedOperationException();
