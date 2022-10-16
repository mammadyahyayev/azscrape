package az.my.datareport.model;

import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;

import java.time.LocalDateTime;

public class FileReport {
    private final String filename;
    private final FileType filetype;
    private final FileExtension fileExtension;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private Report report;


    public FileReport(String filename, FileType filetype, FileExtension fileExtension) {
        this.filename = filename;
        this.filetype = filetype;
        this.fileExtension = fileExtension;
    }

    public String getFilename() {
        return filename;
    }

    public FileType getFiletype() {
        return filetype;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Report getReport() {
        return report;
    }
}
