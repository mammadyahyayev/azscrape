package az.caspian.export.csv;

import az.caspian.core.AzScrapeAppException;
import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.core.utils.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Set;

public class DefaultCsvWriter implements CsvWriter, Closeable {
  private final Writer writer;
  private static final char DEFAULT_CSV_DELIMITER = ',';
  private final char delimiter;

  private int cursorInRow = 0;

  public DefaultCsvWriter(Writer writer) {
    this.writer = writer;
    this.delimiter = DEFAULT_CSV_DELIMITER;
  }

  public DefaultCsvWriter(Writer writer, char delimiter) {
    this.writer = writer;
    this.delimiter = delimiter;
  }

  @Override
  public void writeData(DataTable data, Path filePath) {
    Set<String> columnNames = data.getColumnNames();
    columnNames.forEach(this::writeColumn);

    this.writeNewLine();

    for (DataRow row : data.rows()) {
      for (String columnName : columnNames) {
        row.columns().stream()
            .filter(col -> col.name().equals(columnName))
            .findFirst()
            .map(DataColumn::value)
            .ifPresentOrElse(this::writeColumn, () -> this.writeColumn(" "));
      }

      this.writeNewLine();
    }
  }

  public void writeColumn(String column) {
    try {
      if (cursorInRow != 0) writer.write(delimiter);
      if (!StringUtils.isNullOrEmpty(column)) writer.write(quote(column));
      cursorInRow++;
    } catch (IOException ex) {
      throw new AzScrapeAppException("Failed to write to csv file", ex);
    }
  }

  public void writeNewLine() {
    try {
      writer.write('\n');
      this.cursorInRow = 0;
    } catch (IOException ex) {
      throw new AzScrapeAppException("Failed to write to csv file", ex);
    }
  }

  public char delimiter() {
    return this.delimiter;
  }

  private String quote(String text) {
    return "\"" + text + "\"";
  }

  @Override
  public void close() {
    try {
      this.writer.close();
    } catch (IOException ex) {
      throw new AzScrapeAppException("Failed to close output stream", ex);
    }
  }
}
