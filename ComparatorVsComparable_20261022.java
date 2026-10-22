```java
import java.util.Comparator;
import java.util.List;

public class ComparatorVsComparable {

    public static void main(String[] args) {
        List<Item> items = null;
        sortUsingComparable(items);
        sortUsingComparator(items);
    }

    public static void sortUsingComparable(List<Item> items) {
        // sorting logic using Comparable
    }

    public static void sortUsingComparator(List<Item> items) {
        // sorting logic using Comparator
    }
}

class Item implements Comparable<Item> {
    @Override
    public int compareTo(Item other) {
        return