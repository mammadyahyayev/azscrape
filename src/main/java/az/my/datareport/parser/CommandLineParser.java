package az.my.datareport.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/* TODO:
    1. User can give different flags other than -D, find a good way to represent Flag values
    2. Replace REQUIRED, OPTIONAL with enum
    3. Create ParseException (name can be change) general wrapper exception class
    4. Create multiple Exception class for different parsing scenarios.
    5. Change method names
    6. Restrict access to sensible fields, and don't return sensible values without copying it.
    7. rename getRequiredKeys => requiredKeys
    8. create method for optionalKeys
    9. make lowercase user specified keys before matching
 */
public class CommandLineParser {
    private static final String FLAG = "-D";
    private static final Map<String, PropertyRequirement> APP_PROPERTIES = new HashMap();

    static {
        APP_PROPERTIES.put("config.file.path", PropertyRequirement.REQUIRED);
        APP_PROPERTIES.put("output.file.path", PropertyRequirement.OPTIONAL);
    }

    private String[] cliArguments;
    private Map<String, String> properties = new HashMap<>();

    public CommandLineParser(String[] cliArguments) {
        this.cliArguments = Objects.requireNonNull(cliArguments, "Arguments cannot be null or empty");
    }

    public AppConfig parse() {
        for (String cliArgument : cliArguments) {
            checkFlagExist(cliArgument);

            String argumentWithoutFlag = cliArgument.substring(FLAG.length());

            if (!argumentWithoutFlag.contains("=")) {
                throw new RuntimeException("There is [ " + argumentWithoutFlag + " ] no key value combination!");
            }

            String[] keyValue = argumentWithoutFlag.split("=");
            properties.put(keyValue[0], keyValue[1]);
        }

        List<String> requiredKeys = getRequiredKeys();
        properties.forEach((key, value) -> {
            checkRequirements();

            if (!APP_PROPERTIES.containsKey(key)) {
                throw new RuntimeException("There is no key like [ " + key + " ]");
            }
        });

        String configFilePath = properties.get("config.file.path");
        String outputFilePath = properties.get("output.file.path");
        return new AppConfig(configFilePath, outputFilePath);
    }

    private void checkFlagExist(String argument) {
        if (!argument.contains(FLAG)) {
            throw new RuntimeException("There is no " + FLAG + " flag.");
        }
    }

    public List<String> getRequiredKeys() {
        return APP_PROPERTIES.entrySet().stream()
                .filter(entry -> entry.getValue().equals(PropertyRequirement.REQUIRED)) // TODO: create a method for isKeyRequired
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void checkRequirements() {
        List<String> requiredKeys = getRequiredKeys();

        for (String requiredKey : requiredKeys) {
            if (!properties.containsKey(requiredKey)) {
                throw new RuntimeException(requiredKey + " is required!");
            }
        }
    }

}
