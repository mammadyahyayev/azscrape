package az.caspian.core.model;

import az.caspian.core.model.enumeration.FileExtension;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.utils.AbstractFileSystem;
import az.caspian.core.utils.DefaultFileSystem;

import java.io.File;
import java.time.LocalDateTime;

public class DataFile {
    private final String filename;
    private final FileType filetype;
    private FileExtension fileExtension;
    private String storeAt;
    private final LocalDateTime createdAt = LocalDateTime.now();

    private final AbstractFileSystem abstractFileSystem;

    private DataFile(String filename, FileType filetype, FileExtension fileExtension, String storeAt) {
        this.filename = filename;
        this.filetype = filetype;
        this.fileExtension = fileExtension;
        this.storeAt = storeAt;
        this.abstractFileSystem = new DefaultFileSystem();
    }

    public String getFilename() {
        return filename;
    }

    public String getFileFullName() {
        if (filename.contains(" ")) {
            return abstractFileSystem.createFilename(filename, fileExtension.toString());
        }

        return this.filename.toLowerCase() + "." + this.fileExtension.toString().toLowerCase();
    }

    public FileExtension getFileExtension() {
        return fileExtension;
    }

    public String getFileAbsolutePath() {
        return this.storeAt + File.separator + this.getFileFullName();
    }

    public void setStoreAt(String storeAt) {
        this.storeAt = storeAt;
    }

    public String getStoreAt() {
        return storeAt;
    }

    public static class Builder {
        private String filename;
        private FileType filetype;
        private FileExtension fileExtension;
        private String storeAt;

        public DataFile build() {
            return new DataFile(filename, filetype, fileExtension, storeAt);
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

        public Builder storeAt(String path) {
            this.storeAt = path;
            return this;
        }
    }

}
