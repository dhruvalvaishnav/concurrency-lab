```java
import java.util.*;

public class ComparatorVsComparable {

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(3));
        items.add(new Item(1));
        items.add(new Item(2));

        sortWithComparable(items);
        sortWithComparator(items);
    }

    static class Item implements Comparable<Item> {
        int value;

        Item(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(Item other) {
            return Integer.compare(this.value, other.value);
        }
    }

    static class ItemComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            return