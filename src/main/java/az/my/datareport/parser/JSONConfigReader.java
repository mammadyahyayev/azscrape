package az.my.datareport.parser;

import az.my.datareport.mapper.StringToEnumConverter;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;

public class JSONConfigReader implements ConfigReader {

    private final ConfigFile configFile;

    public JSONConfigReader(ConfigFile configFile) {
        this.configFile = configFile;
    }

    @Override
    public void read() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(Paths.get(configFile.getFilepath()).toFile());
            readReportFileProperties(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readReportFileProperties(JsonNode jsonNode) {
        String title = jsonNode.get("title").asText();
        String exportedFile = jsonNode.get("exported_file").asText();
        String exportedFileExtension = jsonNode.get("exported_file_extension").asText();

        String filename = FileUtility.constructFilename(title);

        FileType fileType = StringToEnumConverter.convert(exportedFile, FileType.class);
        if (fileType == null) {
            throw new UnsupportedFileFormatException(exportedFile + " file format not supported!");
        }

        FileExtension fileExtension = StringToEnumConverter.convert(exportedFileExtension, FileExtension.class);
        if (fileExtension == null) {
            throw new UnsupportedFileFormatException(exportedFile + " file extension not supported!");
        }

        FileReportService fileReportService = new FileReportService();
        fileReportService.generateReportFile(filename, fileType, FileExtension.TXT);
    }
}
