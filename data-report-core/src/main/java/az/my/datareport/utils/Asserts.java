package az.my.datareport.utils;

/**
 * Utility class for assertions, intended for fail fast
 * approach
 */
public final class Asserts {
    private Asserts() {

    }

    /**
     * Check whether given string is null empty, or blank
     *
     * @param str a string
     */
    public static void required(String str) {
        required(str, str + " is required field");
    }

    /**
     * Check whether given string is null empty, or blank
     * and print message
     *
     * @param str     a string
     * @param message exception message
     */
    public static void required(String str, String message) {
        if (str == null || str.isEmpty() || str.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Check whether given object is null and print message
     *
     * @param type    an object
     * @param message exception message
     */
    public static void required(Object type, String message) {
        if (type == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Check whether given class type is null and print message
     *
     * @param type    a class type
     * @param message exception message
     */
    public static void required(Class<?> type, String message) {
        if (type == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that object is not null
     *
     * @param object  can be null
     * @param message exception message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks given expression if it is false, throws  {@link IllegalArgumentException}
     * otherwise doing nothing.
     *
     * @param expression an expression
     * @param message    error message
     */
    public static void checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks given expression if it is false, throws  {@link IllegalArgumentException}
     * otherwise doing nothing.
     *
     * @param expression      an expression
     * @param message         error message
     * @param messageArgument error message parameters
     */
    public static void checkArgument(boolean expression, String message, Object messageArgument) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, messageArgument));
        }
    }
}
