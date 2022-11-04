package az.my.datareport.parser;

import az.my.datareport.ast.DataAST;
import az.my.datareport.converter.StringToEnumConverter;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.scrape.WebScraper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Deprecated
public class JSONConfigReader implements ConfigReader {

    private final ConfigFile configFile;

    public JSONConfigReader(ConfigFile configFile) {
        this.configFile = configFile;
    }

    @Override
    public List<ReportData> read() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(FileUtility.getFile(configFile.getFilepath()));
            ReportFile reportFile = readReportFileProperties(jsonNode);

            JsonNode data = jsonNode.findValue("data");
            if (data == null || data.size() == 0) {
                throw new ConfigNotValidException("config file must contain 'data' field!");
            }

            String url = jsonNode.get("url").asText();
            //TODO: check url is valid url or not

            ASTParser astParser = new ASTParser();
            DataAST dataAST = astParser.parseJsonNode(data);

            WebScraper service = new WebScraper();
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ReportFile readReportFileProperties(JsonNode jsonNode) {
        String title = jsonNode.get("title").asText();
        String exportedFile = jsonNode.get("exported_file").asText();
        String exportedFileExtension = jsonNode.get("exported_file_extension").asText();

        String filename = FileUtility.constructFilename(title);

        FileType fileType = StringToEnumConverter.convert(exportedFile, FileType.class);
        FileExtension fileExtension = StringToEnumConverter.convert(exportedFileExtension, FileExtension.class);

        //TODO: check file type and file format are matched and they are appropriate
        // for example VALID: JSON - json, CSV - xlsx, INVALID: JSON - txt, CSV - json

        return new ReportFile.Builder()
                .filename(filename)
                .fileType(fileType)
                .fileExtension(fileExtension)
                .title(title)
                .build();
    }
}
