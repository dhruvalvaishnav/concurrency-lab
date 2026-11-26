```java
import java.util.*;

public class ComparatorVsComparable {

    public static void main(String[] args) {
        List<Item> items = Arrays.asList(
                new Item("apple", 5),
                new Item("banana", 2),
                new Item("cherry", 7)
        );

        sortByComparable(items);
        sortByComparator(items);
    }

    public static void sortByComparable(List<Item> items) {
        Collections.sort(items);
    }

    public static void sortByComparator(List<Item> items) {
        Collections.sort(items, new ItemNameComparator());
    }
}

class Item implements Comparable<Item> {
    private String name;
    private int value;

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }
    public int getValue() { return value; }

    @Override
    public int compareTo(Item other) {
        return Integer.compare(this.value, other.value);
    }
}

class ItemNameComparator implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        return a.getName().compareTo(b.getName());
    }
}
```