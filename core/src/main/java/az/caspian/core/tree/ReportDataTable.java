package az.caspian.core.tree;

import az.caspian.core.model.DataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportDataTable {
    private final List<DataRow> dataRows;

    public ReportDataTable() {
        this.dataRows = new ArrayList<>();
    }

    public void addAll(List<DataRow> rowsCollection) {
        if (rowsCollection == null || rowsCollection.isEmpty()) {
            return;
        }

        dataRows.addAll(rowsCollection);
    }

    public List<DataRow> rows() {
        return Collections.unmodifiableList(dataRows);
    }
}
