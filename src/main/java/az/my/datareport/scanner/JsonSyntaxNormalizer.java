package az.my.datareport.scanner;

import az.my.datareport.parser.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Used for resolve config file anomalies such as invalid case, invalid delimiter
 */
public class JsonSyntaxNormalizer {

    private static final char FIELD_DELIMITER = '_';

    /**
     * Visits every field and normalize them
     *
     * @param jsonNode object contains json config fields
     * @return normalized object node
     */
    public static ObjectNode normalize(JsonNode jsonNode) {
        Objects.requireNonNull(jsonNode, "jsonNode cannot be null");
        return normalizeFields(jsonNode);
    }

    private static ObjectNode normalizeFields(JsonNode node) {
        ObjectNode objectNode = new ObjectNode(new JsonNodeFactory(false));
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            JsonNode jsonNode = next.getValue();
            String field = normalizeFieldDelimiter(next.getKey().toLowerCase());
            jsonNode = JsonNodeFactoryNormalizer.normalizeNode(jsonNode);
            objectNode.set(field, jsonNode);
        }

        return objectNode;
    }

    /**
     * Replace any special character or separators in field names
     * with supported field delimiter ( {@value FIELD_DELIMITER} ) {@link JsonSyntaxNormalizer#FIELD_DELIMITER}
     *
     * @param field field
     * @return field with supported delimiter
     */
    private static String normalizeFieldDelimiter(String field) {
        if (field == null || field.isEmpty()) return field;

        return StringUtil.replaceAllSymbols(field, FIELD_DELIMITER);
    }

    private static ArrayNode normalizeArrayFields(JsonNode arrayNode) {
        ArrayNode normalizedArrayNode = new ArrayNode(new JsonNodeFactory(false));
        Iterator<JsonNode> elements = arrayNode.elements();

        while (elements.hasNext()) {
            JsonNode next = elements.next();
            JsonNode normalizedNode;

            if (next.isObject()) {
                normalizedNode = normalize(next);
            } else if (next.isArray()) {
                normalizedNode = normalizeArrayFields(next);
            } else {
                normalizedArrayNode.add(next);
                continue;
            }

            normalizedArrayNode.add(normalizedNode);
        }

        return normalizedArrayNode;
    }


    static class JsonNodeFactoryNormalizer {
        public static JsonNode normalizeNode(JsonNode jsonNode) {
            if (jsonNode instanceof ArrayNode) {
                return normalizeArrayFields(jsonNode);
            } else if (jsonNode instanceof TextNode) {
                return jsonNode;
            }

            return normalizeFields(jsonNode);
        }
    }
}
