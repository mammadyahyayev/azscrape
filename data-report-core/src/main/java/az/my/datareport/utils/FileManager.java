package az.my.datareport.utils;

import az.my.datareport.DataReportAppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Works with files
 */
public class FileManager {
    private static final Logger LOG = LogManager.getLogger(FileManager.class);

    private static final char FILE_NAME_DELIMITER = '_';

    public FileManager() {
    }

    /**
     * Construct file if there isn't an appropriate file,
     * if path or directory path is invalid, throws exception
     *
     * @param path given file path
     * @return file
     * @throws DataReportAppException when given path is invalid
     */
    public File constructFile(String path) {
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
     * Construct directory if there isn't an appropriate directory,
     * if path is invalid, throws exception
     *
     * @param path given directory path
     * @return Directory File
     * @throws DataReportAppException when given path is invalid
     */
    public File constructDirectory(String path) {
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

    public String constructFilename(String name) {
        Assert.required(name, "name is required field");

        String filename = name.toLowerCase(Locale.ENGLISH);
        return StringUtils.replaceAllSymbols(filename, FILE_NAME_DELIMITER);
    }

    public String constructFilename(String name, String extension) {
        Assert.required(name, "name is required field");
        Assert.required(extension, "extension is required field");

        String filename = name.trim().toLowerCase(Locale.ENGLISH);
        filename = StringUtils.replaceAllSymbols(filename, FILE_NAME_DELIMITER);
        return filename + "." + extension.trim().toLowerCase();
    }

    public File getFile(String filepath) {
        Assert.required(filepath, "filepath is required");

        File file = new File(filepath);
        if (file.exists() && file.canRead()) {
            return file;
        }

        return null;
    }

}
