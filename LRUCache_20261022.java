```java
import java.util.HashMap;

public class LRUCache {
    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
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

    private class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
        }
    }
}
```