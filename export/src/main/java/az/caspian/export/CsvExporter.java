package az.caspian.export;

import az.caspian.core.DataReportAppException;
import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataFile;
import az.caspian.core.model.DataRow;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.tree.DataTable;
import az.caspian.core.utils.Asserts;
import java.io.*;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvExporter extends AbstractExporter {
  private static final Logger LOG = LogManager.getLogger(CsvExporter.class);

  private static final char DEFAULT_DELIMITER = ',';

  private final char delimiter;

  public CsvExporter() {
    this.delimiter = DEFAULT_DELIMITER;
  }

  public CsvExporter(char delimiter) {
    this.delimiter = delimiter;
  }

  @Override
  public void export(final DataFile dataFile, final DataTable dataTable) {
    Asserts.required(dataFile, "dataFile is required");
    Asserts.required(dataTable, "dataTable is required");

    if (dataFile.getFiletype() != FileType.CSV) {
      throw new IllegalStateException(
          "CSV file type is expecting, but got %s".formatted(dataFile.getFiletype()));
    }

    File file = constructReportFile(dataFile);

    try (var csvWriter = new CsvWriter(new BufferedWriter(new FileWriter(file)), delimiter)) {
      csvWriter.writeData(dataTable);
    } catch (IOException ex) {
      String message = "Failed to write into CSV file [" + file.getAbsolutePath() + "]";
      LOG.error(message, ex);
      throw new DataReportAppException(message, ex);
    }
  }
}
