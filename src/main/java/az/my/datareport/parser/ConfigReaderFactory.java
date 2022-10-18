package az.my.datareport.parser;

public class ConfigReaderFactory {
    public static void read(String configFilePath) {
        if (configFilePath == null || configFilePath.isEmpty()) {
            throw new IllegalArgumentException("file path isn't exist");
        }

        ConfigFile configFile = ConfigFileValidity.validateAndGet(configFilePath);
        ConfigReader reader = getReader(configFile);
        reader.read(configFile);
    }

    private static ConfigReader getReader(ConfigFile configFile) {
        switch (configFile.getFileExtension()) {
            case "json":
                return new JSONConfigReader();
            default:
                assert false;
                throw new UnsupportedFileFormatException(configFile.getFileExtension() + " is unsupported");
        }
    }
}
