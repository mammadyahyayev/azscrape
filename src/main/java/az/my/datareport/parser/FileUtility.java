package az.my.datareport.parser;


public final class FileUtility {
    private static final char FILE_NAME_DELIMITER = '_';

    private FileUtility() {

    }

    public static String constructFilename(String name) {
        String filename = name.toLowerCase();
        return StringUtil.replaceAllSymbols(filename, FILE_NAME_DELIMITER);
    }
}
