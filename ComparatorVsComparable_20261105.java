```java
import java.util.*;

public class ComparatorVsComparable {

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        // populate list
        new ComparatorVsComparable().sortWithComparable(people);
        new ComparatorVsComparable().sortWithComparator(people);
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
            return Integer.compare(this.age, other.age);
        }
    }

    static class PersonNameComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p