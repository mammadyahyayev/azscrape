package az.my.datareport.model;

import az.my.datareport.utils.Asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataRow {
    private List<DataColumn> dataColumns;

    public DataRow() {
        this.dataColumns = new ArrayList<>();
    }

    public void addColumn(DataColumn dataColumn) {
        Asserts.required(dataColumn, "column is required!");

        dataColumns.add(dataColumn);
    }

    public void addColumns(List<DataColumn> dataColumns) {
        this.dataColumns = new ArrayList<>(dataColumns);
    }

    public List<DataColumn> columns() {
        return Collections.unmodifiableList(dataColumns);
    }
}


