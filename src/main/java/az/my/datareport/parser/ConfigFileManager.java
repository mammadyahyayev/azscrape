package az.my.datareport.parser;

import az.my.datareport.config.ConfigFileException;
import az.my.datareport.model.enumeration.ConfigFileExtension;
import az.my.datareport.utils.Assert;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class ConfigFileManager {

    /**
     * Returns ConfigFile object with given path
     *
     * @param path config file path
     * @return ConfigFile object
     * @throws FileNotFoundException          if file doesn't exist
     * @throws UnsupportedFileFormatException if extension of the config file path isn't supported
     * @see ConfigFileExtension
     * @see ConfigFile
     */
    public ConfigFile getConfigFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new ConfigFileException(new FileNotFoundException("Config file with [ " + path + " ] doesn't exist"));
        }

        String filename = FilenameUtils.getName(path);
        String extension = FilenameUtils.getExtension(path);
        if (!ConfigFileExtension.contains(extension)) {
            throw new ConfigFileException(new UnsupportedFileFormatException(extension + " is not supported extension!"));
        }

        return new ConfigFile(filename, path, extension);
    }

    /**
     * Returns ConfigFile object with given path
     *
     * @param path config file path
     * @return ConfigFile object
     * @throws FileNotFoundException          if file doesn't exist
     * @throws UnsupportedFileFormatException if extension of the config file path isn't supported
     * @see ConfigFileExtension
     * @see ConfigFile
     */
    public ConfigFile getConfigFile(Path path) {
        Assert.required(path, "Path cannot be empty or null");
        return getConfigFile(path.toString());
    }
}
