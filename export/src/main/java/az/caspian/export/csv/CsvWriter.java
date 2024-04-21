package az.caspian.export.csv;

import az.caspian.core.tree.DataTable;

import java.io.IOException;
import java.nio.file.Path;

public interface CsvWriter {

  void writeData(DataTable dataTable, Path filePath) throws IOException;

}
