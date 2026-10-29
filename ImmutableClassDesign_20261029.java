```java
public final class ImmutableClassDesign {

    private final int id;
    private final String name;
    private final List<String> tags;

    public ImmutableClassDesign(int id, String name, List<String> tags) {
        this.id = id;
        this.name = name;
        this.tags = List.copyOf(tags);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableClassDesign)) return false;
        ImmutableClassDesign that = (ImmutableClassDesign) o;
        return id == that.id &&
               Objects.equals(name, that.name) &&
               Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tags);
    }

    @Override
    public String toString() {
        return "ImmutableClassDesign{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", tags=" + tags +
               '}';
    }
}
```