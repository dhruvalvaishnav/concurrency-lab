```java
import java.util.HashMap;

public class LRUCache {
    private final int capacity;
    private final HashMap<Integer, Node> map;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        return 0;
    }

    public void put(int key, int value) {
    }

    private void addNode(Node node) {
    }

    private void removeNode(Node node) {
    }

    private void moveToHead(Node node) {
    }

    private Node popTail() {
        return null;
    }

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
```