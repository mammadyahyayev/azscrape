package az.caspian.core.model;

import com.google.common.base.Objects;

import java.io.Serializable;

public record DataColumn(String name, String value) implements Serializable {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataColumn that = (DataColumn) o;
    return Objects.equal(name, that.name) && Objects.equal(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, value);
  }

  @Override
  public String toString() {
    return "DataColumn{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
  }
}
