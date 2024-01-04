package az.caspian.core.model;

import az.caspian.core.utils.Asserts;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataRow implements Serializable {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataRow row = (DataRow) o;
    return Objects.equal(dataColumns, row.dataColumns);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(dataColumns);
  }
}
