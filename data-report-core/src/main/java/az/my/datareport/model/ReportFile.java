package az.my.datareport.model;

import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.utils.FileManager;

import java.time.LocalDateTime;

public class ReportFile {
    private final String filename;
    private final FileType filetype;
    private FileExtension fileExtension;
    private final LocalDateTime createdAt = LocalDateTime.now();

    private final FileManager fileManager;

    private ReportFile(String filename, FileType filetype, FileExtension fileExtension) {
        this.filename = filename;
        this.filetype = filetype;
        this.fileExtension = fileExtension;
        this.fileManager = new FileManager();
    }

    public String getFilename() {
        return filename;
    }

    public String getFileFullName() {
        if (filename.contains(" ")) {
            return fileManager.createFilename(filename, fileExtension.toString());
        }

        return this.filename.toLowerCase() + "." + this.fileExtension.toString().toLowerCase();
    }

    public FileType getFiletype() {
        return filetype;
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(FileExtension extension) {
        this.fileExtension = extension;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private String filename;
        private FileType filetype;
        private FileExtension fileExtension;

        public ReportFile build() {
            return new ReportFile(filename, filetype, fileExtension);
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder fileType(FileType filetype) {
            this.filetype = filetype;
            return this;
        }

        public Builder fileExtension(FileExtension extension) {
            this.fileExtension = extension;
            return this;
        }
    }

}
