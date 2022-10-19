package az.my.datareport.parser;

import az.my.datareport.model.enumeration.FileExtension;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Arrays;

public class ConfigFileValidity {
    private static final String[] SUPPORTED_CONFIG_FILE_EXTENSIONS = {FileExtension.JSON.fileType()};

    public static ConfigFile validateAndGet(String filePath) {
        File file = new File(filePath);

        if (!file.isFile() || !file.exists()) {
            throw new IllegalArgumentException(String.format("File not found or path '%s' isn't refer to a file", filePath));
        }

        String extension = FilenameUtils.getExtension(filePath);
        if (!Arrays.asList(SUPPORTED_CONFIG_FILE_EXTENSIONS).contains(extension)) {
            throw new UnsupportedFileFormatException(extension + " is not supported extension!");
        }

        return new ConfigFile(file.getName(), file.getPath(), extension);
    }

}
