package az.my.datareport.parser;

public class ConfigReaderFactory {
    public static void read(String configFilePath) {
        if (configFilePath == null || configFilePath.isEmpty()) {
            throw new IllegalArgumentException("file path isn't exist");
        }

        ConfigFileValidity.validate(configFilePath);
    }
}
