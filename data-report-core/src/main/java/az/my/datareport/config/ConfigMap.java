package az.my.datareport.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMap {

    private final static Map<Boolean, List<Key>> fields = new HashMap<>();

    static {
        List<Key> requiredKeys = new ArrayList<>();
        requiredKeys.add(RequiredConfigField.DATA);
        requiredKeys.add(RequiredConfigField.NAME);
        requiredKeys.add(RequiredConfigField.ELEMENTS);
        requiredKeys.add(RequiredConfigField.SELECTOR);
        requiredKeys.add(RequiredConfigField.EXPORTED_FILE_TYPE);

        List<Key> optionalKeys = new ArrayList<>();
        optionalKeys.add(OptionalConfigField.TITLE);
        optionalKeys.add(OptionalConfigField.DESCRIPTION);

        fields.put(true, requiredKeys);
        fields.put(false, optionalKeys);
    }

    public static List<Key> getRequiredFields() {
        return new ArrayList<>(fields.get(true));
    }

    public static List<Key> getOptionalFields() {
        return new ArrayList<>(fields.get(false));
    }

    public interface Key {

    }

    enum OptionalConfigField implements Key {
        TITLE, DESCRIPTION
    }

    enum RequiredConfigField implements Key {
        DATA, URL, ELEMENTS, NAME, SELECTOR, EXPORTED_FILE_TYPE
    }

}
