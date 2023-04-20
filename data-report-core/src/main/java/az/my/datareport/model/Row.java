package az.my.datareport.model;

import az.my.datareport.utils.Asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Row {
    private List<Column> columns;

    public Row() {
        this.columns = new ArrayList<>();
    }

    public void addColumn(Column column) {
        Asserts.required(column, "column is required!");

        columns.add(column);
    }

    public void addColumns(List<Column> columns) {
        this.columns = new ArrayList<>(columns);
    }

    public List<Column> columns() {
        return Collections.unmodifiableList(columns);
    }
}


