package az.caspian.export;

import az.caspian.core.DataReportAppException;
import az.caspian.core.constant.FileConstants;
import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.model.ReportFile;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.core.utils.AbstractFileSystem;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.DefaultFileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Exports report data into Excel file
 */
public class ExcelExporter implements Exporter {

    private static final Logger LOG = LogManager.getLogger(ExcelExporter.class);

    @Override
    public boolean export(ReportFile reportFile, ReportDataTable reportData) {
        Objects.requireNonNull(reportFile);
        Asserts.required(reportFile.getFilename(), "Filename is required for report");

        File file = constructReportFile(reportFile);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("data");
            createHeaders(sheet, reportData);
            createValues(sheet, reportData);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            String message = "Failed to write into excel file [" + file.getAbsolutePath() + "]";
            LOG.error(message, e);
            throw new DataReportAppException(message, e);
        }

        return true;
    }

    private void createHeaders(Sheet sheet, ReportDataTable reportDataTable) {
        Row headerRow = sheet.createRow(0);
        List<DataRow> dataRows = reportDataTable.rows();

        if(dataRows.size() == 0) return;

        DataRow first = dataRows.get(0);
        if (first != null) {
            int columnIndex = 0;
            for (DataColumn dataColumn : first.columns()) {
                headerRow.createCell(columnIndex++, CellType.STRING).setCellValue(dataColumn.getName());
            }
        }
    }

    private void createValues(Sheet sheet, ReportDataTable reportData) {
        List<DataRow> dataRows = reportData.rows();
        int rowIndex = 0;

        for (DataRow dataRow : dataRows) {
            int columnIndex = 0;
            for (DataColumn dataColumn : dataRow.columns()) {
                Row valueRow = createOrGetRow(sheet, rowIndex);
                valueRow.createCell(columnIndex++, CellType.STRING).setCellValue(dataColumn.getValue());
            }
            rowIndex++;
        }
    }

    /**
     * @param sheet  sheet of the Excel file
     * @param rowNum Zero based row number, first row number is 0
     * @return row if it exists, or create
     */
    private Row createOrGetRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum + 1);
        if (row == null) {
            row = sheet.createRow(rowNum + 1);
        }
        return row;
    }

    @Override
    public File constructReportFile(final String directoryPath, final ReportFile reportFile) {
        Asserts.required(directoryPath, "Directory path is required");
        Asserts.required(reportFile, "ReportFile is required");

        AbstractFileSystem abstractFileSystem = new DefaultFileSystem();
        abstractFileSystem.createDirectoryIfNotExist(directoryPath);

        String filename = abstractFileSystem.createFilename(reportFile.getFilename());
        String extension = reportFile.getFileExtension().name().toLowerCase();
        Path filepath = Path.of(directoryPath, filename + "." + extension);

        return abstractFileSystem.createFileIfNotExist(filepath.toString());
    }

    @Override
    public File constructReportFile(ReportFile reportFile) {
        String directory = FileConstants.TEMP_DIR_PATH;
        LOG.info("Constructed path for report file [ " + directory + " ]");
        return constructReportFile(directory, reportFile);
    }

}
