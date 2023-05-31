package az.caspian.export;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataFile;
import az.caspian.core.model.DataRow;
import az.caspian.core.model.enumeration.FileExtension;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.core.utils.AbstractFileSystem;
import az.caspian.core.utils.DefaultFileSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ExcelExporterTest {

    DataFile dataFile;

    ExcelExporter exporter;

    AbstractFileSystem abstractFileSystem;

    @BeforeEach
    void beforeEach() {
        dataFile = new DataFile.Builder()
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
        File file = exporter.constructReportFile(dataFile);

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
        Mockito.when(mock.constructReportFile(dataFile)).thenReturn(expectedFile);
        exporter.export(dataFile, reportDataTable);

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