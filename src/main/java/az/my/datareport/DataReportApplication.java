package az.my.datareport;

import az.my.datareport.parser.ConfigFile;
import az.my.datareport.parser.ConfigFileValidity;
import az.my.datareport.parser.JSONConfigReader;
import az.my.datareport.parser.UnsupportedFileFormatException;

public final class DataReportApplication {

    private DataReportApplication() {

    }

    public static void init(String configFilePath) {
        if (configFilePath == null || configFilePath.isEmpty()) {
            throw new NullPointerException("Please specify path of the config file!");
        }

        ConfigFile configFile = ConfigFileValidity.validateAndGet(configFilePath);

        switch (configFile.getFileExtension()) {
            case "json":
                new JSONConfigReader(configFile);
            default:
                assert false;
                throw new UnsupportedFileFormatException(configFile.getFileExtension() + " is unsupported");
        }
    }

}
