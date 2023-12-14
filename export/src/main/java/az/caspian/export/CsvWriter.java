package az.caspian.export;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class CsvWriter implements Closeable {
  private final OutputStream outputStream;
  private static final char DEFAULT_CSV_DELIMITER = ',';
  private final char delimiter;

  public CsvWriter(OutputStream outputStream) {
    this.outputStream = outputStream;
    this.delimiter = DEFAULT_CSV_DELIMITER;
  }

  public CsvWriter(OutputStream outputStream, char delimiter) {
    this.outputStream = outputStream;
    this.delimiter = delimiter;
  }

  public void writeRow(String row) {
    int lastChar = row.length() - 1;
    if (row.charAt(lastChar) == this.delimiter) {
      row = row.substring(0, lastChar);
    }

    try {
      outputStream.write(row.getBytes());
      outputStream.write('\n');
    } catch (IOException ex) {
      // TODO: ignore for now
    }
  }

  @Override
  public void close() {
    try {
      this.outputStream.close();
    } catch (IOException ex) {
      // TODO: Ignore for now
      throw new RuntimeException(ex);
    }
  }
}
