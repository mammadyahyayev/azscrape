package az.my.datareport.model;

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
}
