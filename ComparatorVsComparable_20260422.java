```java
import java.util.Comparator;
import java.util.List;

public class ComparatorVsComparable {

    public static void main(String[] args) {
    }

    public static <T extends Comparable<? super T>> void sortWithComparable(List<T> list) {
    }

    public static <T> void sortWithComparator(List<T> list, Comparator<? super T> comparator) {
    }
}
```