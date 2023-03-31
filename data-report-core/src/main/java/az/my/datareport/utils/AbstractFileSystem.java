package az.my.datareport.utils;

import az.my.datareport.DataReportAppException;
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
        Assert.required(path);

        File file = new File(path);
        try {
            if (file.exists()) {
                throw new FileAlreadyExistsException("Given path [ " + path + " ] is already exist!");
            }
            file.createNewFile();
        } catch (IOException e) {
            LOG.error("Couldn't create file with {}", path);
            throw new DataReportAppException("Couldn't create file with [ " + path + " ]", e);
        }

        return file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File createFileIfNotExist(String path) {
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
     * {@inheritDoc}
     */
    @Override
    public File getFile(String filepath) {
        Assert.required(filepath, "filepath is required");

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
        Assert.required(filepath, "filepath is required");

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
