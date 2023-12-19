package az.caspian.core.tree;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataTable {
  private final List<DataRow> dataRows;

  public DataTable() {
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

  public Set<String> getColumnNames() {
      return this.dataRows.stream()
        .flatMap((row) -> row.columns().stream())
        .collect(Collectors.groupingBy(DataColumn::name))
        .keySet();
  }
}
