package az.my.datareport.scanner;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataNode;
import az.my.datareport.config.ConfigFileException;
import az.my.datareport.parser.ConfigNotValidException;
import az.my.datareport.parser.FileUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class JsonConfigFileScanner implements ConfigFileScanner {

    private static final Logger LOG = LoggerFactory.getLogger(JsonConfigFileScanner.class);

    @Override
    public DataAST read(String filePath) {
        File file = FileUtility.getFile(filePath);
        if (file == null) {
            throw new ConfigFileException(new FileNotFoundException());
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(file);
            if (jsonNode == null || jsonNode instanceof MissingNode) {
                throw new ConfigFileException("Content not found inside of the config file!");
            }

            ObjectNode objectNode = JsonSyntaxNormalizer.normalize(jsonNode);

            //TODO: Implement a class that will check existence of required fields (or add a new method inside SyntaxNormalizer)

            String title = objectNode.get("title").asText();
            String description = objectNode.get("description").asText();
            String fileType = objectNode.get("exported_file_type").asText();

            JsonNode data = objectNode.findValue("data");
            if (data == null || data.size() == 0) {
                throw new ConfigFileException(new ConfigNotValidException("config file must contain 'data' field!"));
            }

            AbstractTreeBuilder abstractTreeBuilder = new AbstractTreeBuilder(data);
            return abstractTreeBuilder.build();
        } catch (IOException e) {
            LOG.error("Can't read from file: " + e);
            throw new RuntimeException(e);
        }
    }

    private static class AbstractTreeBuilder {
        private final JsonNode node;

        public AbstractTreeBuilder(JsonNode node) {
            this.node = node;
        }

        public DataAST build() {
            DataAST tree = new DataAST();
            ObjectMapper mapper = new ObjectMapper();

            try {
                List<DataNode> dataNodes = mapper.readerFor(new TypeReference<List<DataNode>>() {
                }).readValue(node);
                tree.setDataNodes(dataNodes);
            } catch (IOException e) {
                LOG.error("Conversion from node to POJO failed: " + e);
                throw new RuntimeException(e);
            }

            return tree;
        }
    }
}
