package az.my.datareport.parser;

public final class Assert {
    private Assert() {

    }

    public static void required(String str, String message) {
        if (str == null || str.isEmpty() || str.isBlank()) {
            throw new NullPointerException(message);
        }
    }

    public static void required(Class<?> type, String message) {
        if (type == null) {
            throw new NullPointerException(message);
        }
    }
}
