package az.my.datareport.exporter;

import az.my.datareport.DataReportAppException;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
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

public class ExcelExporter implements Exporter {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelExporter.class);

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
        for (int i = 0; i < reportData.getReportDataElements().size(); i++) {
            headerRow.createCell(i, CellType.STRING).setCellValue(reportData.getReportDataElements().get(i).getName());
        }
    }

    private void createValues(Sheet sheet, ReportData reportData) {
        List<ReportDataElement> reportDataElements = reportData.getReportDataElements();
        for (int i = 0; i < reportDataElements.size(); i++) {
            for (int j = 0; j < reportDataElements.get(i).values().size(); j++) {
                Row valueRow = sheet.getRow(j + 1);
                if (valueRow == null) {
                    valueRow = sheet.createRow(j + 1);
                }

                valueRow.createCell(i, CellType.STRING).setCellValue(reportDataElements.get(i).values().get(j));
            }
        }
    }

    @Override
    public File constructReportFile(final String directoryPath, final ReportFile reportFile) {
        Assert.required(directoryPath, "Directory path is required");
        Assert.required(reportFile, "ReportFile is required");

        FileManager fileManager = new FileManager();
        fileManager.constructDirectory(directoryPath);

        String filename = FileUtility.constructFilename(reportFile.getFilename());
        String extension = reportFile.getFileExtension().name().toLowerCase();
        String filepath = directoryPath + File.separator + filename + "." + extension;

        return fileManager.constructFile(filepath);
    }

    @Override
    public File constructReportFile(ReportFile reportFile) {
        String currDir = System.getProperty("user.dir");
        String directory = currDir + "/src/main/resources";
        return constructReportFile(directory, reportFile);
    }

}
