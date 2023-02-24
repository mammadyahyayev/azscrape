package az.my.datareport.scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonSyntaxNormalizerTest {

    @Test
    void testNormalize_whenAbnormalKeysGiven_returnNormalizeFormOfThem() {
        // given
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("test-resources/test-config.json")) {
            if (inputStream == null) {
                fail("test-config.json was not found");
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(inputStream);
            ObjectNode node = JsonSyntaxNormalizer.normalize(jsonNode);
            assertNull(node.get("TitLe"));
            assertNotNull(node.get("title"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}