package az.my.datareport.exporter;

import az.my.datareport.constant.FileConstants;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportDataParent;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.utils.AbstractFileSystem;
import az.my.datareport.utils.DefaultFileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ExcelExporterTest {

    ReportFile reportFile;

    ExcelExporter exporter;

    AbstractFileSystem abstractFileSystem;

    @BeforeEach
    void beforeEach() {
        reportFile = new ReportFile.Builder()
                .filename("GitHub SearcH")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        exporter = new ExcelExporter();
        abstractFileSystem = new DefaultFileSystem();
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    void testConstructReportFile_whenDirectoryPathGiven_constructAndReturnFile() {
        //given
        Path directory = Path.of("C:", "Users", "User", "Desktop", "test-report");
        String expectedFileName = "github_search.xlsx";
        String expectedFilePath = Path.of(directory.toString(), expectedFileName).toString();

        //when
        File file = exporter.constructReportFile(directory.toString(), reportFile);

        //then
        assertEquals(expectedFileName, file.getName());
        assertEquals(expectedFilePath, file.getAbsolutePath());

        // delete generated file and directory
        deleteFile(expectedFilePath);
        deleteFile(directory.toString());
    }


    @DisplayName("Create file on resources folder inside the application")
    @Test
    void testConstructReportFile_whenAppDirectoryPathGiven_constructAndReturnFile() {
        //given
        String expectedFileName = "github_search.xlsx";
        Path expectedFilePath = Path.of(FileConstants.TEMP_DIR_PATH, expectedFileName);

        // when
        File file = exporter.constructReportFile(reportFile);

        // then
        assertEquals(expectedFileName, file.getName());
        assertEquals(expectedFilePath.toString(), file.getAbsolutePath());
    }

    @Test
    void testExport_whenGivingReportDataColumns_thenWriteThemIntoExcelFile() {
        //given
        Path path = Path.of(FileConstants.TEMP_DIR_PATH, "github_search.xlsx");

        ReportData reportData = new ReportData();

        ReportDataParent parent = new ReportDataParent();
        ReportDataElement titleJava9 = new ReportDataElement("title", "Java9");
        ReportDataElement descJava9 = new ReportDataElement("description", "Desc Java9");
        parent.setReportDataElements(List.of(titleJava9, descJava9));

        ReportDataParent parent2 = new ReportDataParent();
        ReportDataElement titleJava8 = new ReportDataElement("title", "Java8");
        ReportDataElement descJava8 = new ReportDataElement("description", "Desc Java8");
        parent2.setReportDataElements(List.of(titleJava8, descJava8));

        ReportDataParent parent3 = new ReportDataParent();
        ReportDataElement titleJava7 = new ReportDataElement("title", "Java7");
        ReportDataElement descJava7 = new ReportDataElement("description", "");
        parent3.setReportDataElements(List.of(titleJava7, descJava7));

        ReportDataParent parent4 = new ReportDataParent();
        ReportDataElement titleJava6 = new ReportDataElement("title", "");
        ReportDataElement descJava6 = new ReportDataElement("description", "Desc Java6");
        parent4.setReportDataElements(List.of(titleJava6, descJava6));

        reportData.setReportParentElements(List.of(parent, parent2, parent3, parent4));

        //when
        File expectedFile = new File(path.toString());
        ExcelExporter mock = mock(ExcelExporter.class);
        Mockito.when(mock.constructReportFile(reportFile)).thenReturn(expectedFile);
        exporter.export(reportFile, reportData);

        //then
        File file = new File(path.toString());
        assertTrue(file.exists());
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() || file.isDirectory()) {
            file.delete();
        }
    }
}