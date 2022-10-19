package az.my.datareport.parser;

public class ConfigReaderFactory {
    public static ConfigReader getReader(String configFilePath) {
        if (configFilePath == null || configFilePath.isEmpty()) {
            throw new NullPointerException("Please specify path of the config file!");
        }

        ConfigFile configFile = ConfigFileValidity.validateAndGet(configFilePath);

        switch (configFile.getFileExtension()) {
            case "json":
                return new JSONConfigReader(configFile);
            default:
                assert false;
                throw new UnsupportedFileFormatException(configFile.getFileExtension() + " is unsupported");
        }
    }
}
