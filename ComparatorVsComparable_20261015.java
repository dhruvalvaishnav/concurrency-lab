```java
import java.util.*;

public class ComparatorVsComparable {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
        );

        sortByComparable(people);
        sortByComparator(people);
    }

    public static void sortByComparable(List<Person> list) {
        Collections.sort(list);
    }

    public static void sortByComparator(List<Person> list) {
        Collections.sort(list, new AgeComparator());
    }

    static class Person implements Comparable<Person> {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Person other) {
            return this.name.compareTo(other.name);
        }
    }

    static class AgeComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return Integer.compare(p1.age, p2.age);
        }
    }
}
```