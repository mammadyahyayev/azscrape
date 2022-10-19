package az.my.datareport.parser;

public final class StringUtil {
    private static final char[] SYMBOLS = {'.', ' ', '#', '$', '@', ',', '-', '_'};

    private StringUtil() {

    }

    public static String replaceAllSymbols(String str, char delimiter) {
        for (char symbol : SYMBOLS) {
            str = str.replace(symbol, delimiter);
        }

        return str;
    }
}
