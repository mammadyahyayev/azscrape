package az.my.datareport.model;

import java.util.ArrayList;
import java.util.List;

//TODO: values is sensible variable, add helper methods
// and remove setters
public class ReportDataElement {
    private final String name;
    private final List<String> values; //TODO: replace this with Generics

    public ReportDataElement(String name, List<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public List<String> values() {
        return new ArrayList<>(values);
    }
}
