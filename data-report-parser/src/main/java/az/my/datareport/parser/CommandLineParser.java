package az.my.datareport.parser;

import az.my.datareport.utils.Asserts;

import java.util.*;
import java.util.stream.Collectors;

/* TODO:
    1. User can give different flags other than -D, find a good way to represent Flag values
    6. Restrict access to sensible fields, and don't return sensible values without copying it.
    9. make lowercase user specified keys before matching
 */
public class CommandLineParser {
    private static final String FLAG = "-D"; // -D means define
    private static final Map<String, PropertyRequirement> APP_PROPERTIES = new HashMap<>();

    static {
        APP_PROPERTIES.put("config.file.path", PropertyRequirement.REQUIRED);
        APP_PROPERTIES.put("output.file.path", PropertyRequirement.OPTIONAL);
    }

    private final String[] cliArguments;

    public CommandLineParser(String[] cliArguments) {
        this.cliArguments = Objects.requireNonNull(cliArguments, "Arguments cannot be null or empty");
    }

    public AppConfig parse() {
        Map<String, String> definedProperties = splitArguments();

        for (String requiredKey : requiredKeys()) {
            if (!definedProperties.containsKey(requiredKey)) {
                throw new ParseException("Please define " + requiredKey + " because  it is required key!");
            } else {
                String value = definedProperties.get(requiredKey);
                Asserts.required(value, requiredKey + " value can not be null or empty!");
            }
        }

        AppConfig config = new AppConfig(definedProperties);
        return config.load();
    }

    private Map<String, String> splitArguments() {
        Map<String, String> properties = new HashMap<>();
        for (String argument : cliArguments) {
            checkFlagExist(argument);

            String[] values = splitArgument(argument, FLAG);
            String property = values[1].trim();

            if (!hasKeyValueCombination(property)) {
                throw new ParseException("There is [ " + property + " ] no key value combination!");
            }

            String[] keyValue = splitArgument(property, "=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            properties.put(key, value);
        }

        return properties;
    }

    private String[] splitArgument(final String argument, final String delimiter) {
        if (!argument.contains(delimiter)) {
            throw new ParseException("There is no " + delimiter + ".");
        }

        return argument.split(delimiter);
    }

    private void checkFlagExist(String argument) {
        if (!argument.contains(FLAG)) {
            throw new ParseException("There is no " + FLAG + " flag.");
        }
    }

    private boolean hasKeyValueCombination(String property) {
        return property.contains("=");
    }


    public List<String> requiredKeys() {
        return getKeys(PropertyRequirement.REQUIRED);
    }

    public List<String> optionalKeys() {
        return getKeys(PropertyRequirement.OPTIONAL);
    }

    public List<String> getKeys() {
        return new ArrayList<>(APP_PROPERTIES.keySet());
    }

    public List<String> getKeys(PropertyRequirement keyRequirement) {
        return APP_PROPERTIES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(keyRequirement))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
