package az.my.datareport.parser;

public final class ConfigReaderFactory {

    private ConfigReaderFactory() {

    }

    public static ConfigReader jsonConfigReader(ConfigFile configFile) {
        return new JSONConfigReader(configFile);
    }
}
