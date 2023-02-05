package az.my.datareport.service;

import az.my.datareport.config.ConfigLoader;
import az.my.datareport.converter.StringToEnumConverter;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.tree.DataAST;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final ConfigLoader loader;

    public ConfigService(ConfigLoader loader) {
        this.loader = loader;
    }


    public DataAST sendConfigStr(String json) {
        return loader.loadConfig(json);
    }

    public ReportFile getFileConfiguration() {
        ConfigLoader.TempConfig config = loader.getReportFileConfiguration();

        FileType fileType = StringToEnumConverter.convert(config.getExportedFileType(), FileType.class);
        FileExtension fileExtension = StringToEnumConverter.convert(config.getExportedFileType(), FileExtension.class);

        return new ReportFile.Builder()
                .filename(config.getExportedFileName())
                .fileType(fileType)
                .fileExtension(fileExtension)
                .build();
    }

}
