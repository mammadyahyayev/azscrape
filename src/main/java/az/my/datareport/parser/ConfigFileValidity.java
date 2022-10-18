package az.my.datareport.parser;

import az.my.datareport.model.enumeration.FileExtension;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class ConfigFileValidity {
    private static final FileExtension[] SUPPORTED_CONFIG_FILE_EXTENSION = {FileExtension.JSON};

    public static void validate(String filePath) {
        File file = new File(filePath);
        if (!file.isFile() || file.exists()) {
            throw new IllegalArgumentException(String.format("File not found or path %s isn't refer to a file", filePath));
        }

        isSupportedFileExtension(file);
    }

    private static void isSupportedFileExtension(File file) {
        String extension = FilenameUtils.getExtension(file.getPath());

        for (var supported : SUPPORTED_CONFIG_FILE_EXTENSION) {
            if (!supported.fileType().equals(extension)) {
                throw new UnsupportedFileFormatException(extension + " is not supported extension!");
            }
        }
    }

}
