package az.caspian.core.model;

public class DataColumn {
    private final String name;
    private final String value;

    public DataColumn(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DataColumn{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
