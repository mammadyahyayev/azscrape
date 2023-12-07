package az.caspian.core.model;

public record DataColumn(String name, String value) {
  @Override
  public String toString() {
    return "DataColumn{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
  }
}
