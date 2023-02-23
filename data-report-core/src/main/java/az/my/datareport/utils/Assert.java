package az.my.datareport.utils;

/**
 * Utility class for assertions, intended for fail fast
 * approach
 */
public final class Assert {
    private Assert() {

    }

    public static void required(String str) {
        required(str, str + " is required field");
    }

    public static void required(String str, String message) {
        if (str == null || str.isEmpty() || str.isBlank()) {
            throw new AssertionError(message);
        }
    }

    public static void required(Object type, String message) {
        if (type == null) {
            throw new AssertionError(message);
        }
    }

    public static void required(Class<?> type, String message) {
        if (type == null) {
            throw new AssertionError(message);
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
}
