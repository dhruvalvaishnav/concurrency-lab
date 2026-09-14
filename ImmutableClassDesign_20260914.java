```java
public final class ImmutableClass {

    private final int id;
    private final String name;
    private final java.util.List<String> tags;

    public ImmutableClass(int id, String name, java.util.List<String> tags) {
        this.id = id;
        this.name = name;
        this.tags = java.util.Collections.unmodifiableList(new java.util.ArrayList<>(tags));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public java.util.List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableClass)) return false;
        ImmutableClass that = (ImmutableClass) o;
        return id == that.id &&
               java.util.Objects.equals(name, that.name) &&
               java.util.Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name, tags);
    }

    @Override
    public String