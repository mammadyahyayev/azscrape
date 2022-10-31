package az.my.datareport.scanner;

import az.my.datareport.parser.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class JsonSyntaxNormalizer {

    public static ObjectNode normalize(JsonNode jsonNode) {
        Objects.requireNonNull(jsonNode, "jsonNode cannot be null");
        return normalizeFields(jsonNode);
    }

    private static ObjectNode normalizeFields(JsonNode node) {
        ObjectNode objectNode = new ObjectNode(new JsonNodeFactory(false));
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            String field = normalizeFieldDelimiter(next.getKey().toLowerCase());

            if (next.getValue().isArray()) {
                ArrayNode arrayFields = normalizeArrayFields(next.getValue());
                objectNode.set(field, arrayFields);
            } else if (next.getValue().isObject()) {
                ObjectNode normalizedObjectNode = normalizeObjectFields(next.getValue());
                objectNode.set(field, normalizedObjectNode);
            } else {
                objectNode.set(field, next.getValue());
            }
        }

        return objectNode;
    }

    private static Map<String, JsonNode> normalizeField(String key, JsonNode node) {
        //TODO: check field is required or not, throw exception if it is required, do nothing if it is optional

        Map<String, JsonNode> field = new HashMap<>();
        field.put(key, node);
        return field;
    }

    private static String normalizeFieldDelimiter(String field) {
        if (field == null || field.isEmpty()) return field;

        return StringUtil.replaceAllSymbols(field, '_');
    }

    private static ArrayNode normalizeArrayFields(JsonNode arrayNode) {
        if (arrayNode instanceof ArrayNode) {
            ArrayNode normalizedArrayNode = new ArrayNode(new JsonNodeFactory(false));
            Iterator<JsonNode> elements = arrayNode.elements();

            if (!elements.hasNext()) {
                arrayNode.elements().forEachRemaining(normalizedArrayNode::add);
                return normalizedArrayNode;
            }

            while (elements.hasNext()) {
                JsonNode next = elements.next();
                if (next.isObject()) {
                    ObjectNode objectNode = normalizeObjectFields(next);
                    normalizedArrayNode.add(objectNode);
                } else if (next.isArray()) {
                    ArrayNode arrayFields = normalizeArrayFields(next);
                    normalizedArrayNode.add(arrayFields);
                } else {
                    normalizedArrayNode.add(next);
                }
            }

            return normalizedArrayNode;
        }

        return null;
    }

    private static ObjectNode normalizeObjectFields(JsonNode objectNode) {
        if (objectNode instanceof ObjectNode) {
            Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
            ObjectNode newObjectNode = new ObjectNode(new JsonNodeFactory(false));

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> next = fields.next();
                String field = normalizeFieldDelimiter(next.getKey().toLowerCase());
                if (next.getValue().isObject()) {
                    ObjectNode subNode = normalizeObjectFields(next.getValue());
                    newObjectNode.set(field, subNode);
                } else if (next.getValue().isArray()) {
                    ArrayNode arrayFields = normalizeArrayFields(next.getValue());
                    newObjectNode.set(field, arrayFields);
                } else {
                    newObjectNode.set(field, next.getValue());
                }
            }

            return newObjectNode;
        }

        return null;
    }

    //TODO: normalize object fields which are inside of the array such as element, name, selector
    //TODO: normalize fields which are inside of another object.

}
