package az.caspian.client.ui.components;

public interface TableColumn {
  String getName();

  static String[] columnNames(Class<? extends Enum<? extends TableColumn>> enumClass) {
    Enum<?>[] enums = enumClass.getEnumConstants();
    String[] columnNames = new String[enums.length];
    for (int i = 0; i < enums.length; i++) {
      columnNames[i] = ((TableColumn) enums[i]).getName();
    }
    return columnNames;
  }
}
