package az.my.datareport.parser;

import az.my.datareport.ast.DataAST;
import az.my.datareport.mapper.StringToEnumConverter;
import az.my.datareport.model.FileReport;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.scrape.WebScrapingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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

            List<JsonNode> data = jsonNode.findValues("data");
            if (data == null || data.size() == 0) {
                throw new ConfigNotValidException("config file must contain 'data' field!");
            }

            String url = jsonNode.get("url").asText();
            //TODO: check url is valid url or not

            ASTParser astParser = new ASTParser();
            DataAST dataAST = astParser.parseJSON(data);

            WebScrapingService service = new WebScrapingService();
            List<ReportData> reportDataList = service.scrape(url, dataAST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileReport readReportFileProperties(JsonNode jsonNode) {
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

        //TODO: check file type and file format are matched and they are appropriate
        // for example VALID: JSON - json, CSV - xlsx, INVALID: JSON - txt, CSV - json

        return new FileReport(filename, fileType, fileExtension);
    }
}
