package az.my.datareport.utils;

/**
 * Utility for Strings
 */
public final class StringUtils {

    //TODO: Move this functionality into File specific class
    /**
     * Symbols must be replaced if they are in the filename
     */
    private static final char[] SYMBOLS = {'.', ' ', '#', '$', '@', ',', '-', '_'};

    private StringUtils() {

    }

    /**
     * Replace preset {@link StringUtils#SYMBOLS} with
     * given delimiter
     *
     * @param str       given string
     * @param delimiter all symbols will be replaced with
     * @return formatted string
     */
    public static String replaceAllSymbols(String str, char delimiter) {
        for (char symbol : SYMBOLS) {
            str = str.replace(symbol, delimiter);
        }

        return str;
    }
}
