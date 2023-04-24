package az.my.datareport.exporter;

import az.my.datareport.constant.FileConstants;
import az.my.datareport.model.DataColumn;
import az.my.datareport.model.DataRow;
import az.my.datareport.model.ReportFile;
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

        DataRow dataRow1 = new DataRow();
        DataColumn titleJava9 = new DataColumn("title", "Java9");
        DataColumn descJava9 = new DataColumn("description", "Desc Java9");
        dataRow1.addColumns(List.of(titleJava9, descJava9));

        DataRow dataRow2 = new DataRow();

        DataColumn titleJava8 = new DataColumn("title", "Java8");
        DataColumn descJava8 = new DataColumn("description", "Desc Java8");
        dataRow2.addColumns(List.of(titleJava8, descJava8));

        DataRow dataRow3 = new DataRow();
        DataColumn titleJava7 = new DataColumn("title", "Java7");
        DataColumn descJava7 = new DataColumn("description", "");
        dataRow3.addColumns(List.of(titleJava7, descJava7));

        DataRow dataRow4 = new DataRow();
        DataColumn titleJava6 = new DataColumn("title", "");
        DataColumn descJava6 = new DataColumn("description", "Desc Java6");
        dataRow4.addColumns(List.of(titleJava6, descJava6));

        reportDataTable.addAll(List.of(dataRow1, dataRow2, dataRow3, dataRow4));

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