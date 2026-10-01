```java
public final class ImmutableClassDesign {

    private final String name;
    private final int value;
    private final java.util.List<String> items;

    public ImmutableClassDesign(String name, int value, java.util.List<String> items) {
        this.name = name;
        this.value = value;
        this.items = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(items));
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public java.util.List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableClassDesign)) return false;
        ImmutableClassDesign that = (ImmutableClassDesign) o;
        return value == that.value &&
               java.util.Objects.equals(name, that.name) &&
               java.util.Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, value, items);
    }

    @Override
    public String toString() {
        return "ImmutableClassDesign{" +
               "name='" + name + '\'' +
               ", value=" + value +
               ", items=" + items +
               '}';
    }
}
```