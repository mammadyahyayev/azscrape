package az.my.datareport.model;

import java.util.List;

public class ReportData {
    private final String name;
    private final List<Object> values; //TODO: replace this with Generics

    public ReportData(String name, List<Object> values) {
        this.name = name;
        this.values = values;
    }
}
