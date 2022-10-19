package az.my.datareport.parser;

public final class Assert {
    private Assert() {

    }

    public static void required(String str, String field) {
        if (str == null || str.isEmpty() || str.isBlank()) {
            throw new NullPointerException(field + " is required field!");
        }
    }
}
