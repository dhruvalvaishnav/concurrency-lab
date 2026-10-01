```java
public class ComparatorVsComparable {

    public static void main(String[] args) {
        sortWithComparable();
        sortWithComparator();
    }

    public static void sortWithComparable() {
        // sorting logic using Comparable
    }

    public static void sortWithComparator() {
        // sorting logic using Comparator
    }

    static class Person implements Comparable<Person> {
        String name;
        int age;

        @Override
        public int compareTo(Person other) {
            return 0;
        }
    }

    static class PersonComparator implements java.util.Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return 0;
        }
    }
}
```