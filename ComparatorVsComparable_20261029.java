```java
import java.util.*;

public class ComparatorVsComparable {

    public static <T extends Comparable<? super T>> void sortWithComparable(List<T> list) {
        Collections.sort(list);
    }

    public static <T> void sortWithComparator(List<T> list, Comparator<? super T> comparator) {
        Collections.sort(list, comparator);
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 2, 9, 1);
        sortWithComparable(numbers);

        List<String> words = Arrays.asList("banana", "apple", "cherry");
        sortWithComparator(words, Comparator.naturalOrder());
    }
}
```