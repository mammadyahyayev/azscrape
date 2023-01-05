package az.my.datareport.scanner;

import az.my.datareport.constant.TestConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class JsonSyntaxNormalizerTest {

    @Test
    void should_return_normalized_keys() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(new File(TestConstants.TEST_CONFIG_FILE_PATH.toString()));
            ObjectNode node = JsonSyntaxNormalizer.normalize(jsonNode);
            assertNull(node.get("TitLe"));
            assertNotNull(node.get("title"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}