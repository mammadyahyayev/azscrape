package az.my.datareport.scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class JsonSyntaxNormalizerTest {

    @Test
    void testNormalize_whenAbnormalKeysGiven_returnNormalizeFormOfThem() {
        //given
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("test-resources/test-config.json");
        if (resource == null) {
            fail("empty-config.json was not found");
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(new File(resource.getPath()));
            ObjectNode node = JsonSyntaxNormalizer.normalize(jsonNode);
            assertNull(node.get("TitLe"));
            assertNotNull(node.get("title"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}