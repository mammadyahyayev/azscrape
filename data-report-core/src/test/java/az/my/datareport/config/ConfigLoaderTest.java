package az.my.datareport.config;

import az.my.datareport.tree.DataAST;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigLoaderTest {

    @Test
    void testLoadConfig_whenCorrectJsonStringGiven_returnDataAST() {
        String json = "{\n" +
                "  \"exported_file_name\": \"Github Search\",\n" +
                "  \"exported_file_type\": \"EXCEL\",\n" +
                "  \"exported_file_type_extension\": \"xlsx\",\n" +
                "  \"description\": \"Dummy Description\",\n" +
                "  \"data\": {\n" +
                "    \"url\": \"https://github.com/search?q=java\",\n" +
                "    \"element\": {\n" +
                "      \"selector\": \".repo-list-item\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"title\",\n" +
                "          \"selector\": \".v-align-middle\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"description\",\n" +
                "          \"selector\": \".mb-1\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";


        ConfigLoader loader = new ConfigLoader();
        DataAST ast = loader.loadConfig(json);

        Assertions.assertNotNull(ast);
    }

    @Test
    void testLoadConfig_whenEmptyConfigFieldGiven_throwException() {
        String json = "{\n" +
                "  \"exported_file_name\": \"\",\n" +
                "  \"exported_file_type\": \"EXCEL\",\n" +
                "  \"exported_file_type_extension\": \"xlsx\",\n" +
                "  \"description\": \"Dummy Description\",\n" +
                "  \"data\": {\n" +
                "    \"url\": \"https://github.com/search?q=java\",\n" +
                "    \"element\": {\n" +
                "      \"selector\": \".repo-list-item\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"title\",\n" +
                "          \"selector\": \".v-align-middle\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"description\",\n" +
                "          \"selector\": \".mb-1\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";


        ConfigLoader loader = new ConfigLoader();
        Assertions.assertThrows(ConfigurationException.class, () -> loader.loadConfig(json));
    }


}