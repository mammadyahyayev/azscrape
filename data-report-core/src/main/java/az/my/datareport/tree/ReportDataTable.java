package az.my.datareport.tree;

import az.my.datareport.model.Row;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportDataTable {
    private final List<Row> rows;

    public ReportDataTable() {
        this.rows = new ArrayList<>();
    }

    public void addAll(List<Row> rowsCollection) {
        if (rowsCollection == null || rowsCollection.isEmpty()) {
            return;
        }

        rows.addAll(rowsCollection);
    }

    public List<Row> rows() {
        return Collections.unmodifiableList(rows);
    }
}
