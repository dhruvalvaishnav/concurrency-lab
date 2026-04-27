```java
public final class ImmutableClassDesign {

    private final String field1;
    private final int field2;
    private final List<String> field3;

    private ImmutableClassDesign(Builder builder) {
        this.field1 = builder.field1;
        this.field2 = builder.field2;
        this.field3 = Collections.unmodifiableList(new ArrayList<>(builder.field3));
    }

    public String getField1() {
        return field1;
    }

    public int getField2() {
        return field2;
    }

    public List<String> getField3() {
        return field3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableClassDesign)) return false;
        ImmutableClassDesign that = (ImmutableClassDesign) o;
        return field2 == that.field2 &&
               Objects.equals(field1, that.field1) &&
               Objects.equals(field3, that.field3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field1, field2, field3);
    }

    @Override
    public String toString() {
        return "ImmutableClassDesign{" +
               "field1='" + field1 + '\'' +
