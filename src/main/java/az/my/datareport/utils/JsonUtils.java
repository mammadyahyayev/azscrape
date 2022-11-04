package az.my.datareport.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

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
