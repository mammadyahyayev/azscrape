package az.my.datareport.parser;


import az.my.datareport.utils.Assert;
import az.my.datareport.utils.StringUtils;

import java.io.File;

public final class FileUtility {
    private static final char FILE_NAME_DELIMITER = '_';

    private FileUtility() {

    }

    public static String constructFilename(String name) {
        String filename = name.toLowerCase();
        return StringUtils.replaceAllSymbols(filename, FILE_NAME_DELIMITER);
    }

    public static String constructFilename(String name, String extension) {
        Assert.required(name, "name is required field");
        Assert.required(extension, "extension is required field");

        String filename = name.toLowerCase();
        filename = StringUtils.replaceAllSymbols(filename, FILE_NAME_DELIMITER);
        return filename + "." + extension;
    }

    public static File getFile(String filepath) {
        Assert.required(filepath, "filepath is required to construct File object");

        File file = new File(filepath);
        if (file.exists() && file.canRead()) {
            return file;
        }

        return null;
    }
}
