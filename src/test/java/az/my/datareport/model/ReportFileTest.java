package az.my.datareport.model;

import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportFileTest {

    @Test
    void builderTest() {
        ReportFile reportFile = new ReportFile.Builder()
                .filename("test")
                .fileType(FileType.TEXT)
                .fileExtension(FileExtension.TXT)
                .title("Test")
                .build();

        assertEquals("test", reportFile.getFilename());
        assertEquals("Test", reportFile.getTitle());
        assertEquals(FileExtension.TXT, reportFile.getFileExtension());
        assertEquals(FileType.TEXT, reportFile.getFiletype());
    }

}