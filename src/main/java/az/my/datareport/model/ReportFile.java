package az.my.datareport.model;

import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;

import java.time.LocalDateTime;

public class ReportFile {
    private final String filename;
    private final FileType filetype;
    private final String title;
    private FileExtension fileExtension;
    private final LocalDateTime createdAt = LocalDateTime.now();


    private ReportFile(String filename, FileType filetype, FileExtension fileExtension, String title) {
        this.filename = filename;
        this.filetype = filetype;
        this.fileExtension = fileExtension;
        this.title = title;
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

    public void setFileExtension(FileExtension extension) {
        this.fileExtension = extension;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {
        private String filename;
        private FileType filetype;
        private FileExtension fileExtension;
        private String title;

        public ReportFile build() {
            return new ReportFile(filename, filetype, fileExtension, title);
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

        public Builder title(String title) {
            this.title = title;
            return this;
        }

    }

}
