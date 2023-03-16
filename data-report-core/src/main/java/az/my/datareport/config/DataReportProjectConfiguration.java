package az.my.datareport.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataReportProjectConfiguration {
    private static final Logger LOG = LogManager.getLogger(DataReportProjectConfiguration.class);

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_FOLDER_PATH = Path.of(USER_HOME, ".ym");

    public void init() {
        if (checkYmFolderExist()) {
            return;
        }

        try {
            Files.createDirectory(YM_FOLDER_PATH);
        } catch (IOException e) {
            LOG.error(String.format("Failed to create .ym folder inside %s", USER_HOME));
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }


    private boolean checkYmFolderExist() {
        File file = new File(YM_FOLDER_PATH.toString());
        return file.exists();
    }

}
