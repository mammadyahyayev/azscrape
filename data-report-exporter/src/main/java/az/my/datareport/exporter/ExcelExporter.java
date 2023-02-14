package az.my.datareport.exporter;

import az.my.datareport.DataReportAppException;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportDataParent;
import az.my.datareport.model.ReportFile;
import az.my.datareport.utils.Assert;
import az.my.datareport.utils.FileManager;
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

public class ExcelExporter implements Exporter {

    private static final Logger LOG = LogManager.getLogger(ExcelExporter.class);

    @Override
    public boolean export(ReportFile reportFile, ReportData reportData) {
        Objects.requireNonNull(reportFile);
        Assert.required(reportFile.getFilename(), "Filename is required for report");

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

    private void createHeaders(Sheet sheet, ReportData reportData) {
        Row headerRow = sheet.createRow(0);
        List<ReportDataParent> reportParentElements = reportData.getReportParentElements();

        ReportDataParent first = reportParentElements.get(0);
        if (first != null && first.getReportDataElements().size() > 0) {
            int column = 0;
            for (ReportDataElement element : first.getReportDataElements()) {
                headerRow.createCell(column++, CellType.STRING).setCellValue(element.getName());
            }
        }
    }

    private void createValues(Sheet sheet, ReportData reportData) {
        List<ReportDataParent> parentElements = reportData.getReportParentElements();
        int row = 0;
        for (ReportDataParent parentElement : parentElements) {
            int column = 0;
            for (ReportDataElement element : parentElement.getReportDataElements()) {
                Row valueRow = createOrGetRow(sheet, row);
                valueRow.createCell(column++, CellType.STRING).setCellValue(element.getValue());
            }
            row++;
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
        Assert.required(directoryPath, "Directory path is required");
        Assert.required(reportFile, "ReportFile is required");

        FileManager fileManager = new FileManager();
        fileManager.constructDirectory(directoryPath);

        String filename = FileManager.constructFilename(reportFile.getFilename());
        String extension = reportFile.getFileExtension().name().toLowerCase();
        Path filepath = Path.of(directoryPath, filename + "." + extension);

        return fileManager.constructFile(filepath.toString());
    }

    @Override
    public File constructReportFile(ReportFile reportFile) {
        String currDir = System.getProperty("user.dir");
        Path directory = Path.of(currDir, "data-report-core", RESOURCE_DIR.toString());
        LOG.info("Constructed path for report file [ " + directory + " ]");
        return constructReportFile(directory.toString(), reportFile);
    }

}
