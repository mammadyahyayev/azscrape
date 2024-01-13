package az.caspian.core.utils;

import az.caspian.core.AzScrapeAppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

/**
 * Works with files
 * <br/>
 * <br/>
 * <h3>Acceptable File norm example</h3>
 * <b>Given:</b> fiLe Operation  <br/>
 * <b>Return:</b> file_operation <br/>
 */
public abstract class AbstractFileSystem implements FileSystem {
  private static final Logger LOG = LogManager.getLogger(AbstractFileSystem.class);

  /**
   * Used to replace all file symbols with _ (underscore)
   */
  private static final char FILE_NAME_DELIMITER = '_';

  public AbstractFileSystem() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createFile(String path) {
    Asserts.required(path);

    File file = new File(path);
    try {
      if (file.exists()) {
        throw new FileAlreadyExistsException("Given path [ " + path + " ] is already exist!");
      }
      file.createNewFile();
    } catch (IOException e) {
      LOG.error("Couldn't create file with {}", path);
      throw new AzScrapeAppException("Couldn't create file with [ " + path + " ]", e);
    }

    return file;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createFileIfNotExist(String path) {
    Asserts.required(path);

    File file = new File(path);
    if (file.exists()) {
      if (file.isFile()) {
        return file;
      }

      throw new AzScrapeAppException("Given path [ " + path + " ] isn't file!");
    }

    try {
      file.createNewFile();
      LOG.debug("File " + path + " is created...");
    } catch (IOException e) {
      LOG.error("Couldn't create file with {}", path);
      throw new AzScrapeAppException("Couldn't create file with [ " + path + " ]", e);
    }

    return file;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createDirectory(String path) {
    Asserts.required(path);
    File file = new File(path);

    try {
      if (file.exists()) {
        throw new FileAlreadyExistsException("Given path [ " + path + " ] is already exist!");
      }

      file.mkdirs();
    } catch (IOException e) {
      LOG.error("Couldn't create directory with {}", path);
      throw new AzScrapeAppException("Failed to create directory with " + path, e);
    }

    return file;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createDirectoryIfNotExist(String path) {
    Asserts.required(path);
    File file = new File(path);

    if (file.exists()) {
      if (file.isDirectory()) {
        return file;
      }

      throw new AzScrapeAppException("Given path [" + path + "] isn't directory");
    }

    boolean isDirCreated = file.mkdirs();
    if (!isDirCreated) {
      LOG.error("Couldn't create directory with {}", path);
      throw new AzScrapeAppException("Couldn't create directory with [ " + path + " ]");
    }

    return file;
  }

  /**
   * Creates filename with given string, and replaces all symbols
   * with underscore symbol
   *
   * @param name name of the file without extension
   * @return file name with acceptable form
   * @see #FILE_NAME_DELIMITER
   */
  public String createFilename(String name) {
    Asserts.required(name, "name is required field");

    String filename = name.trim().toLowerCase(Locale.ENGLISH);
    return StringUtils.replaceAllSymbols(filename, FILE_NAME_DELIMITER);
  }

  /**
   * Creates full filename with given name and extension, and replaces all symbols
   * with underscore symbol
   *
   * @param name name of the file without extension
   * @return file name with acceptable form
   * @see #createFilename(String)
   */
  public String createFilename(String name, String extension) {
    Asserts.required(name, "name is required field");
    Asserts.required(extension, "extension is required field");

    String filename = createFilename(name);
    return filename + "." + extension.trim().toLowerCase();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File getFile(String filepath) {
    Asserts.required(filepath, "filepath is required");

    File file = new File(filepath);
    if (file.exists() && file.canRead()) {
      return file;
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteFile(Path filepath) {
    Asserts.required(filepath, "filepath is required");

    try {
      return Files.deleteIfExists(filepath);
    } catch (IOException e) {
      LOG.error("Failed to delete file!", e);
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFileExist(String path) {
    File file = new File(path);
    return file.exists();
  }

}
