```java
public final class ImmutableClassDesign {

    private final String name;
    private final int age;
    private final List<String> tags;

    public ImmutableClassDesign(String name, int age, List<String> tags) {
        this.name = name;
        this.age = age;
        this.tags = tags == null ? Collections.emptyList() : List.copyOf(tags);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableClassDesign)) return false;
        ImmutableClassDesign that = (ImmutableClassDesign) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, tags);
    }

    @Override
    public String toString() {
        return "ImmutableClassDesign{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", tags=" + tags +
                '}';
    }
}
```