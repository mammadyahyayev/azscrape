package az.my.datareport.parser;

import java.io.File;

public interface ConfigReader {
    void read(String configFilePath);

    void read(File configFile);

    void read(ConfigFile configFile);
}
