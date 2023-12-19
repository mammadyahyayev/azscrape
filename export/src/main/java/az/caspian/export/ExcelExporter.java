package az.caspian.export;

import az.caspian.core.DataReportAppException;
import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataFile;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.core.utils.Asserts;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** Exports report data into Excel file */
public class ExcelExporter extends AbstractExporter {

  private static final Logger LOG = LogManager.getLogger(ExcelExporter.class);

  @Override
  public void export(DataFile dataFile, DataTable reportData) {
    Objects.requireNonNull(dataFile);
    Asserts.required(dataFile.getFilename(), "Filename is required for report");

    File file = constructReportFile(dataFile);

    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("data");
      writeDataToSheet(sheet, reportData);
      FileOutputStream outputStream = new FileOutputStream(file);
      workbook.write(outputStream);
      outputStream.close();
    } catch (IOException e) {
      String message = "Failed to write into excel file [" + file.getAbsolutePath() + "]";
      LOG.error(message, e);
      throw new DataReportAppException(message, e);
    }
  }

  private void writeDataToSheet(Sheet sheet, DataTable dataTable) {
    Row headerRow = sheet.createRow(0);
    List<DataRow> dataRows = dataTable.rows();

    if (dataRows.isEmpty()) return;

    Set<String> columns =
        dataRows.stream()
            .flatMap(row -> row.columns().stream())
            .collect(Collectors.groupingBy(DataColumn::name))
            .keySet();

    int rowIndex = 0;
    for (DataRow dataRow : dataRows) {
      int columnIndex = 0;
      for (String column : columns) {
        if (rowIndex == 0) {
          headerRow.createCell(columnIndex, CellType.STRING).setCellValue(column);
        }

        String value =
            dataRow.columns().stream()
                .filter(c -> c.name().equals(column))
                .findFirst()
                .map(DataColumn::value)
                .orElse("");

        Row valueRow = createOrGetRow(sheet, rowIndex);
        valueRow.createCell(columnIndex, CellType.STRING).setCellValue(value);

        columnIndex += 1;
      }

      rowIndex++;
    }
  }

  /**
   * @param sheet sheet of the Excel file
   * @param rowNum Zero-based row number, first row number is 0
   * @return row if it exists, or create
   */
  private Row createOrGetRow(Sheet sheet, int rowNum) {
    Row row = sheet.getRow(rowNum + 1);
    if (row == null) {
      row = sheet.createRow(rowNum + 1);
    }
    return row;
  }
}
