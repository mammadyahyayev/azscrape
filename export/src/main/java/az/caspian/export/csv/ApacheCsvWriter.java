package az.caspian.export.csv;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

public class ApacheCsvWriter implements CsvWriter {

  @Override
  public void writeData(DataTable data, Path filePath) throws IOException {
    Set<String> columnNames = data.getColumnNames();

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
      .setHeader(columnNames.toArray(new String[0]))
      .build();

    try (var csvPrinter = new CSVPrinter(new BufferedWriter(new FileWriter(filePath.toFile())), csvFormat)) {
      for (DataRow row : data.rows()) {
        for (String columnName : columnNames) {
          Optional<String> value = row.columns().stream()
            .filter(col -> col.name().equals(columnName))
            .findFirst()
            .map(DataColumn::value);

          csvPrinter.print(value.orElse(" "));
        }

        csvPrinter.println();
      }
    }
  }
}
