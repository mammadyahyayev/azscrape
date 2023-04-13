package az.my.datareport.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
     * @param str       {@code String}
     * @param delimiter all symbols will be replaced with
     * @return formatted string
     */
    public static String replaceAllSymbols(String str, char delimiter) {
        for (char symbol : SYMBOLS) {
            str = str.replace(symbol, delimiter);
        }

        return str;
    }

    /**
     * Combines two objects into String object
     *
     * @param str1 first {@code String}
     * @param str2 second {@code String}
     * @return combined String
     */
    public static String combine(@NotNull Object str1, @NotNull Object str2) {
        return str1 + str2.toString();
    }

    /**
     * Gives last index of String object
     *
     * @param str A {@code String}
     * @return last index of String object
     */
    public static int lastIndex(@NotNull String str) {
        if (str.isEmpty() || str.isBlank()) {
            return 0;
        }

        return str.charAt(str.length() - 1);
    }

    /**
     * Checks whether string is null, empty or blank.
     *
     * @param str A {@code String}
     * @return true if string is not null or empty, otherwise false
     */
    public static boolean isNullOrEmpty(@Nullable String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }
}
