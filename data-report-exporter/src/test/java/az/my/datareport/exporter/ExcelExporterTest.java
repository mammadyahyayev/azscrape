package az.my.datareport.exporter;

import az.my.datareport.constant.FileConstants;
import az.my.datareport.model.Column;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.Row;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.tree.ReportDataTable;
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

        ReportDataTable reportDataTable = new ReportDataTable();

        Row row1 = new Row();
        Column titleJava9 = new Column("title", "Java9");
        Column descJava9 = new Column("description", "Desc Java9");
        row1.addColumns(List.of(titleJava9, descJava9));

        Row row2 = new Row();

        Column titleJava8 = new Column("title", "Java8");
        Column descJava8 = new Column("description", "Desc Java8");
        row2.addColumns(List.of(titleJava8, descJava8));

        Row row3 = new Row();
        Column titleJava7 = new Column("title", "Java7");
        Column descJava7 = new Column("description", "");
        row3.addColumns(List.of(titleJava7, descJava7));

        Row row4 = new Row();
        Column titleJava6 = new Column("title", "");
        Column descJava6 = new Column("description", "Desc Java6");
        row4.addColumns(List.of(titleJava6, descJava6));

        reportDataTable.addAll(List.of(row1, row2, row3, row4));

        //when
        File expectedFile = new File(path.toString());
        ExcelExporter mock = mock(ExcelExporter.class);
        Mockito.when(mock.constructReportFile(reportFile)).thenReturn(expectedFile);
        exporter.export(reportFile, reportDataTable);

        //then
        File file = new File(path.toString());
        assertTrue(file.exists());
        System.out.println(path);
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists() || file.isDirectory()) {
            file.delete();
        }
    }
}