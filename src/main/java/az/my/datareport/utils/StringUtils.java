package az.my.datareport.utils;

public final class StringUtils {
    private static final char[] SYMBOLS = {'.', ' ', '#', '$', '@', ',', '-', '_'};

    private StringUtils() {

    }

    public static String replaceAllSymbols(String str, char delimiter) {
        for (char symbol : SYMBOLS) {
            str = str.replace(symbol, delimiter);
        }

        return str;
    }
}
