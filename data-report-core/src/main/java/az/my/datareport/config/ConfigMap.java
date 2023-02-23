package az.my.datareport.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigMap {

    private static final Map<Boolean, List<Field>> fields = new HashMap<>();

    static {
        List<Field> requiredFields = new ArrayList<>();
        requiredFields.add(RequiredConfigField.DATA);
        requiredFields.add(RequiredConfigField.NAME);
        requiredFields.add(RequiredConfigField.ELEMENT);
        requiredFields.add(RequiredConfigField.SELECTOR);
        requiredFields.add(RequiredConfigField.EXPORTED_FILE_TYPE);
        requiredFields.add(RequiredConfigField.EXPORTED_FILE_TYPE_EXTENSION);

        List<Field> optionalFields = new ArrayList<>();
        optionalFields.add(OptionalConfigField.EXPORTED_FILE_NAME);
        optionalFields.add(OptionalConfigField.DESCRIPTION);

        fields.put(true, requiredFields);
        fields.put(false, optionalFields);
    }

    public static List<Field> requiredFields() {
        return new ArrayList<>(fields.get(true));
    }

    public static List<Field> optionalFields() {
        return new ArrayList<>(fields.get(false));
    }

    enum OptionalConfigField implements Field {
        EXPORTED_FILE_NAME, DESCRIPTION
    }

    enum RequiredConfigField implements Field {
        DATA, URL, ELEMENT, NAME, SELECTOR, EXPORTED_FILE_TYPE, EXPORTED_FILE_TYPE_EXTENSION
    }

    public interface Field {

    }

}
