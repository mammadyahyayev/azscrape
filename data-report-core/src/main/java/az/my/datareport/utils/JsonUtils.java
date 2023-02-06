package az.my.datareport.utils;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

public final class JsonUtils {

    private JsonUtils() {

    }

    public static List<String> getAllFieldKeys(JsonNode node) {
        if (node == null) {
            return Collections.emptyList();
        }

        List<String> keys = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        fields.forEachRemaining(x -> keys.add(x.getKey()));

        return keys;
    }

}
