package az.caspian.core.model;

import az.caspian.core.model.enumeration.FileExtension;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.utils.AbstractFileSystem;
import az.caspian.core.utils.DefaultFileSystem;
import az.caspian.core.utils.StringUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class DataFile {
  private final String filename;
  private final FileType filetype;
  private final FileExtension fileExtension;
  private String storeAt;
  private final LocalDateTime createdAt = LocalDateTime.now();
  private final Charset charset;

  private DataFile(
      String filename,
      FileType filetype,
      FileExtension fileExtension,
      String storeAt,
      Charset charset) {
    this.filename = filename;
    this.filetype = filetype;
    this.fileExtension = fileExtension;
    this.storeAt = storeAt;
    this.charset = charset != null ? charset : Charset.defaultCharset();
  }

  public String getFilename() {
    return filename;
  }

  public String getFileFullName() {
    return this.filename.toLowerCase() + "." + this.fileExtension.toString().toLowerCase();
  }

  public FileExtension getFileExtension() {
    return switch (filetype) {
      case CSV -> FileExtension.CSV;
      case EXCEL -> FileExtension.XLSX;
    };
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

  public FileType getFiletype() {
    return filetype;
  }

  public Charset getCharset() {
    return charset;
  }

  public static class Builder {
    private String filename;
    private FileType filetype;
    private FileExtension fileExtension;
    private String storeAt;
    private Charset charset;

    public DataFile build() {
      return new DataFile(filename, filetype, fileExtension, storeAt, charset);
    }

    public Builder filename(String filename) {
      if (StringUtils.isNullOrEmpty(filename)) {
        throw new IllegalArgumentException("filename cannot be null or empty!");
      }
      this.filename = filename;
      return this;
    }

    public Builder fileType(FileType filetype) {
      this.filetype = filetype;
      return this;
    }

    /**
     * @deprecated use {@link #fileType(FileType)} method, the extension will be created
     *     automatically.
     */
    @Deprecated(since = "3.0.0", forRemoval = true)
    public Builder fileExtension(FileExtension extension) {
      this.fileExtension = extension;
      return this;
    }

    public Builder charset(Charset charset) {
      this.charset = charset;
      return this;
    }

    public Builder storeAt(String path) {
      this.storeAt = path;
      return this;
    }
  }
}
