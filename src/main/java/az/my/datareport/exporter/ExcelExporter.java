package az.my.datareport.exporter;

import az.my.datareport.DataReportAppException;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportDataParent;
import az.my.datareport.model.ReportFile;
import az.my.datareport.parser.FileUtility;
import az.my.datareport.utils.Assert;
import az.my.datareport.utils.FileManager;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ExcelExporter implements Exporter {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelExporter.class);
    private static final String RESOURCE_DIR = "/src/main/resources";

    @Override
    public void export(ReportFile reportFile, ReportData reportData) {
        Objects.requireNonNull(reportFile);
        Assert.required(reportFile.getFilename(), "Filename is required for report");

        File file = constructReportFile(reportFile);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("data");
            createHeaders(sheet, reportData);
            createValues(sheet, reportData);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException e) {
            String message = "Failed to write into excel file [" + file.getAbsolutePath() + "]";
            LOG.error(message, e);
            throw new DataReportAppException(message, e);
        }
    }

    private void createHeaders(Sheet sheet, ReportData reportData) {
        Row headerRow = sheet.createRow(0);
        List<ReportDataParent> reportParentElements = reportData.getReportParentElements();

        Optional<ReportDataParent> first = reportParentElements.stream().findFirst();
        if (first.isPresent()) {
            ReportDataParent parent = first.get();
            int column = 0;
            for (ReportDataElement element : parent.getReportDataElements()) {
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

        String filename = FileUtility.constructFilename(reportFile.getFilename());
        String extension = reportFile.getFileExtension().name().toLowerCase();
        String filepath = directoryPath + "/" + filename + "." + extension;

        return fileManager.constructFile(filepath);
    }

    @Override
    public File constructReportFile(ReportFile reportFile) {
        String currDir = System.getProperty("user.dir");
        String directory = currDir + RESOURCE_DIR;
        return constructReportFile(directory, reportFile);
    }

}
