```java
import java.util.*;

public class ComparatorVsComparable {

    public static void sortWithComparable(List<Integer> list) {
    }

    public static void sortWithComparator(List<Integer> list) {
    }

    public static void main(String[] args) {
    }

    static class MyComparable implements Comparable<MyComparable> {
        @Override
        public int compareTo(MyComparable o) {
            return 0;
        }
    }

    static class MyComparator