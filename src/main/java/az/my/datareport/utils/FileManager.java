package az.my.datareport.utils;

import az.my.datareport.DataReportAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Works with files
 */
public class FileManager {
    private static final Logger LOG = LoggerFactory.getLogger(FileManager.class);

    public FileManager() {
    }

    /**
     * Construct file if there isn't an appropriate file,
     * if path is invalid or directory path, throws exception
     *
     * @throws DataReportAppException when given path is invalid
     * @param path given file path
     * @return file
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
     * if path is invalid or file path, throws exception
     *
     * @throws DataReportAppException when given path is invalid
     * @param path given directory path
     * @return Directory File
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

}
