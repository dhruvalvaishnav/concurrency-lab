```java
import java.util.*;

public class ComparatorVsComparable {

    public static void main(String[] args) {
        List<Sample> list = new ArrayList<>();
        sortWithComparable(list);
        sortWithComparator(list);
    }

    public static void sortWithComparable(List<Sample> list) {
        Collections.sort(list);
    }

    public static void sortWith