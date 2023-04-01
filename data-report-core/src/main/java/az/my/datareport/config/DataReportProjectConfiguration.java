package az.my.datareport.config;

import az.my.datareport.utils.FileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class DataReportProjectConfiguration {
    private static final Logger LOG = LogManager.getLogger(DataReportProjectConfiguration.class);

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_DIRECTORY_PATH = Path.of(USER_HOME, ".ym");
    private static final Path YM_PROPERTIES_FILE_PATH = Path.of(YM_DIRECTORY_PATH.toString(), ".ym.properties");

    private final FileSystem fileSystem;

    public DataReportProjectConfiguration(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public void initConfig() {
        File dir = fileSystem.createDirectory(YM_DIRECTORY_PATH.toString());
        System.setProperty(".ym", dir.getAbsolutePath());
        LOG.debug(".ym directory created in {}", dir.getAbsolutePath());

        File configFile = fileSystem.createFileIfNotExist(YM_PROPERTIES_FILE_PATH.toString());
        System.setProperty("config.properties.path", configFile.getAbsolutePath());
        LOG.debug("config.properties.path created in {}", configFile.getAbsolutePath());
    }
}
