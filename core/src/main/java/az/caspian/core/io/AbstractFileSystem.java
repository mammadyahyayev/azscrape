package az.caspian.core.io;

import az.caspian.core.AzScrapeAppException;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.StringUtils;
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

  protected AbstractFileSystem() {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createFile(Path path) {
    checkPathNotNull(path);

    try {
      if (Files.exists(path)) {
        throw new FileAlreadyExistsException("Path [ " + path + " ] is already exist!");
      }
      Files.createFile(path);
    } catch (IOException e) {
      LOG.error("Couldn't create file with {}", path);
      throw new AzScrapeAppException("Couldn't create file with [ " + path + " ]", e);
    }

    return path.toFile();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createFileIfNotExist(Path path) {
    checkPathNotNull(path);

    if (Files.exists(path)) {
      if (Files.isRegularFile(path)) {
        return path.toFile();
      }

      throw new AzScrapeAppException("Given path [ " + path + " ] isn't file!");
    }

    try {
      Files.createFile(path);
      LOG.debug("File {} is created...", path);
    } catch (IOException e) {
      LOG.error("Couldn't create file with {}", path);
      throw new AzScrapeAppException("Couldn't create file with [ " + path + " ]", e);
    }

    return path.toFile();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createDirectory(Path path) {
    checkPathNotNull(path);

    try {
      if (Files.exists(path)) {
        throw new FileAlreadyExistsException("Given path [ " + path + " ] is already exist!");
      }

      Files.createDirectories(path);
    } catch (IOException e) {
      LOG.error("Couldn't create directory with {}", path);
      throw new AzScrapeAppException("Failed to create directory with " + path, e);
    }

    return path.toFile();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File createDirectoryIfNotExist(Path path) {
    checkPathNotNull(path);

    try {
      if (Files.exists(path)) {
        if (Files.isDirectory(path)) {
          return path.toFile();
        }

        throw new AzScrapeAppException("Given path [" + path + "] isn't directory");
      }

      Files.createDirectory(path);
    } catch (IOException e) {
      LOG.error("Couldn't create directory with {}", path);
      throw new AzScrapeAppException("Couldn't create directory with [ " + path + " ]");
    }

    return path.toFile();
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
  public File getFile(Path path) {
    checkPathNotNull(path);

    if (Files.exists(path) && Files.isReadable(path)) {
      return path.toFile();
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean deleteFile(Path path) {
    checkPathNotNull(path);

    try {
      return Files.deleteIfExists(path);
    } catch (IOException e) {
      LOG.error("Failed to delete file!", e);
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFileExist(Path path) {
    checkPathNotNull(path);
    return Files.exists(path);
  }

  /**
   * Helper method to check given {@link Path} is not null. It is created to avoid duplications of checking
   * in each method.
   *
   * @param path path of file
   */
  private static void checkPathNotNull(Path path) {
    Asserts.required(path, "path cannot be null!");
  }
}
