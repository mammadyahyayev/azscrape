package az.my.datareport.exporter;

import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.utils.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcelExporterTest {

    ReportFile reportFile;

    ExcelExporter exporter;

    FileManager fileManager;

    @BeforeEach
    void beforeEach() {
        reportFile = new ReportFile.Builder()
                .filename("GitHub SearcH")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        exporter = new ExcelExporter();
        fileManager = new FileManager();
    }

    @Test
    void testConstructReportFile_whenDirectoryPathGiven_constructAndReturnFile() {
        //given
        String directory = "C:\\Users\\User\\Desktop\\test-report";
        String expectedFileName = "github_search.xlsx";
        String expectedFilePath = directory + File.separator + expectedFileName;

        //when
        File file = exporter.constructReportFile(directory, reportFile);

        //then
        assertEquals(expectedFileName, file.getName());
        assertEquals(expectedFilePath, file.getAbsolutePath());
    }


    @DisplayName("Create file on resources folder inside the application")
    @Test
    void testConstructReportFile_whenAppDirectoryPathGiven_constructAndReturnFile() {
        //given
        String directory = System.getProperty("user.dir") + File.separator + "src\\main\\resources";
        String expectedFileName = "github_search.xlsx";
        String expectedFilePath = directory + "\\" + expectedFileName;

        // when
        File file = exporter.constructReportFile(reportFile);

        // then
        assertEquals(expectedFileName, file.getName());
        assertEquals(expectedFilePath, file.getAbsolutePath());
    }

    @Test
    void testExport_whenGivingReportDataColumns_thenWriteThemIntoExcelFile() {
        String path = "C:\\Users\\User\\Desktop\\data-report\\src\\main\\resources\\github_search.xlsx";
        ReportData reportData = new ReportData();
        reportData.setUrl(null);
        ReportDataElement title = new ReportDataElement("title", List.of("Java9", "Java11", "Java8"));
        ReportDataElement description = new ReportDataElement("description", List.of("Desc Java9", "Desc Java11", "Desc Java8"));
        reportData.setReportDataElements(List.of(title, description));

        File expectedFile = new File(path);

        ExcelExporter mock = org.mockito.Mockito.mock(ExcelExporter.class);

        Mockito.when(mock.constructReportFile(reportFile)).thenReturn(expectedFile);

        exporter.export(reportFile, reportData);

        File file = new File(path);
        assertTrue(file.exists());
    }
}