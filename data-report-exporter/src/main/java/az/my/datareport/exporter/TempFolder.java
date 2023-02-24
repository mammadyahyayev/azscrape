package az.my.datareport.exporter;

import az.my.datareport.utils.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A temporary folder, responsible to store exported files
 */
public class TempFolder {
    private static final Logger LOG = LogManager.getLogger(ExcelExporter.class);

    private static final String TEMP_FILE_NAME = "__exports__";
    private Path tempDirPath;

    /**
     * Creates a temporary folder
     *
     * @return true if temp folder created successfully, otherwise false
     */
    public boolean createTempDirectory() {
        try {
            this.tempDirPath = Files.createTempDirectory(TEMP_FILE_NAME);
        } catch (IOException e) {
            LOG.error("Failed to create temp directory", e);
            throw new IllegalStateException("Failed to create temp directory", e);
        }

        return true;
    }

    /**
     * Creates file inside of temp folder
     *
     * @param filename given filename with extension
     * @return created file path
     */
    public Path createFile(String filename) {
        Assert.required(filename);

        if (!isExist()) {
            createTempDirectory();
        }

        Path filepath = Path.of(this.tempDirPath.toString(), filename);

        try {
            Files.createFile(filepath);
            return filepath;
        } catch (IOException e) {
            String message = String.format("Failed to create file %s inside of %s temp folder", filepath, this.tempDirPath);
            LOG.error(message);
            throw new IllegalStateException(message, e);
        }
    }

    /**
     * Returns temp directory path
     *
     * @return created temp folder path
     */
    public Path getPath() {
        return this.tempDirPath;
    }

    public boolean isExist() {
        return this.tempDirPath != null;
    }

    /**
     * Deletes temp folder
     */
    public boolean deleteOnExit() {
        //TODO: delete file inside of temp folder, then delete temp folder itself
        try {
            Files.deleteIfExists(this.tempDirPath);
            LOG.debug("Temp folder {} deleted successfully", this.tempDirPath);
            return true;
        } catch (IOException e) {
            LOG.error("Failed to delete temp {} directory", this.tempDirPath);
            throw new IllegalStateException(String.format("Failed to delete temp %s directory", this.tempDirPath));
        }
    }
}
