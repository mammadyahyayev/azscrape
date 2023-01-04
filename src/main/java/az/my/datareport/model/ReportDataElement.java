package az.my.datareport.model;

//TODO: values is sensible variable, add helper methods
// and remove setters
public class ReportDataElement {
    private final String name;
    private final String value;

    public ReportDataElement(String name, String value) {
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
        return "ReportDataElement{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
