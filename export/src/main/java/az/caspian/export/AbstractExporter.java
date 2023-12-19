package az.caspian.export;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.model.DataFile;
import az.caspian.core.utils.AbstractFileSystem;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.DefaultFileSystem;
import java.io.File;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractExporter implements Exporter {
  private static final Logger LOG = LogManager.getLogger(AbstractExporter.class);

  /**
   * Constructs ReportFile that is going to being used to store scraped data
   *
   * @param directoryPath given directory path, can be absolute or relative
   * @param dataFile exported file
   * @return constructed output file for export
   */
  protected File constructReportFile(final String directoryPath, final DataFile dataFile) {
    Asserts.required(directoryPath, "Directory path is required");
    Asserts.required(dataFile, "ReportFile is required");

    AbstractFileSystem abstractFileSystem = new DefaultFileSystem();
    abstractFileSystem.createDirectoryIfNotExist(directoryPath);

    String filename = abstractFileSystem.createFilename(dataFile.getFilename());
    String extension = dataFile.getFileExtension().name().toLowerCase();
    Path filepath = Path.of(directoryPath, filename + "." + extension);

    return abstractFileSystem.createFileIfNotExist(filepath.toString());
  }

  /**
   * Constructs ReportFile in project resource folder
   *
   * @param dataFile exported file
   * @return constructed output file for export
   */
  protected File constructReportFile(DataFile dataFile) {
    if (dataFile.getStoreAt() == null) {
      dataFile.setStoreAt(FileConstants.TEMP_DIR_PATH);
    }

    LOG.info("Constructed path for report file [ " + dataFile.getStoreAt() + " ]");
    return constructReportFile(dataFile.getStoreAt(), dataFile);
  }
}
