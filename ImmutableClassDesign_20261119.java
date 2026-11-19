```java
public final class ImmutableClassDesign {

    private final String name;
    private final int value;
    private final List<String> items;

    public ImmutableClassDesign(String name, int value, List<String> items) {
        this.name = name;
        this.value = value;
        this.items = List.copyOf(items);
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableClassDesign)) return false;
        ImmutableClassDesign that = (ImmutableClassDesign) o;
        return value == that.value &&
               Objects.equals(name, that.name) &&
               Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, items);
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