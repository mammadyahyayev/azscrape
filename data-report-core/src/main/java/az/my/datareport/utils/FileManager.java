package az.my.datareport.utils;

import az.my.datareport.DataReportAppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
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
public class FileManager {
    private static final Logger LOG = LogManager.getLogger(FileManager.class);

    /**
     * Used to replace all file symbols with _ (underscore)
     */
    private static final char FILE_NAME_DELIMITER = '_';

    public FileManager() {
    }

    /**
     * Creates file if there isn't an appropriate file,
     * if path or directory path is invalid, throws exception
     *
     * @param path given file path
     * @return file
     * @throws DataReportAppException when given path is invalid
     */
    public File createFile(String path) {
        Assert.required(path);

        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                return file;
            }
            throw new DataReportAppException("Given path [ " + path + " ] isn't file!");
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            LOG.error("Couldn't create file with {}", path);
            throw new DataReportAppException("Couldn't create file with [ " + path + " ]", e);
        }

        return file;
    }

    /**
     * Creates directory if there isn't an appropriate directory,
     * if path is invalid, throws exception
     *
     * @param path given directory path
     * @return Directory File
     * @throws DataReportAppException when given path is invalid
     */
    public File createDirectory(String path) {
        Assert.required(path);
        File file = new File(path);

        if (file.exists()) {
            if (file.isDirectory()) {
                return file;
            }

            throw new DataReportAppException("Given path [" + path + "] isn't directory");
        }

        boolean isDirCreated = file.mkdirs();
        if (!isDirCreated) {
            LOG.error("Couldn't create directory with {}", path);
            throw new DataReportAppException("Couldn't create directory with [ " + path + " ]");
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
        Assert.required(name, "name is required field");

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
        Assert.required(name, "name is required field");
        Assert.required(extension, "extension is required field");

        String filename = createFilename(name);
        return filename + "." + extension.trim().toLowerCase();
    }

    /**
     * Gets filepath as string, and checks file exists and available to read,
     * then return the file as <code>File</code> object, otherwise returns <code>null</code>.
     *
     * @param filepath path of the file, can be absolute or relative
     * @return <code>File</code> object
     */
    public File getFile(String filepath) {
        Assert.required(filepath, "filepath is required");

        File file = new File(filepath);
        if (file.exists() && file.canRead()) {
            return file;
        }

        return null;
    }

    /**
     * Deletes file with given path
     *
     * @param filepath path of the file
     * @return true if file deleted successfully, otherwise false
     */
    public boolean deleteFile(Path filepath) {
        Assert.required(filepath, "filepath is required");

        try {
            return Files.deleteIfExists(filepath);
        } catch (IOException e) {
            LOG.error("Failed to delete file!", e);
            return false;
        }
    }

    /**
     * Checks whether file exists or not
     *
     * @param path a path of file
     * @return true if file exists, otherwise false
     */
    public boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Checks whether directory exists or not
     *
     * @param path a path of directory
     * @return true if directory exists, otherwise false
     */
    public boolean isDirectoryExist(String path) {
        File file = new File(path);
        return file.exists();
    }

}
