package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Fonts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DefaultTable extends JTable {
  private final String[] columnNames;
  private final Object[][] data;

  public DefaultTable(Class<? extends Enum<? extends TableColumn>> enumClass, Object[][] data) {
    super(data, TableColumn.columnNames(enumClass));

    this.columnNames = TableColumn.columnNames(enumClass);
    this.data = data;

    super.getTableHeader().setReorderingAllowed(false);
    super.getTableHeader().setOpaque(false);
    super.getTableHeader().setForeground(Color.WHITE);
    super.getTableHeader().setFont(Fonts.SANS_SERIF_BOLD_18);

    super.getTableHeader().setBackground(new Color(0x36304A));

    super.setModel(new DefaultTableModel(data, columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });

    super.setRowHeight(40);
    super.setFocusable(false);
    super.setBackground(Color.WHITE);
    super.setForeground(Color.BLACK);
    super.setSelectionBackground(new Color(0xB1AFAF));
    super.setFont(Fonts.SANS_SERIF_PLAIN_16);
    super.setShowGrid(false);
    super.setIntercellSpacing(new Dimension(0, 0));
  }

  public String[] getColumnNames() {
    return columnNames;
  }

  public void makeColumnEditable(int columnIndex) {
    super.setModel(new DefaultTableModel(data, columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return columnIndex == column;
      }
    });
  }
}
