package az.my.datareport.parser;


import java.util.Map;
import java.util.Objects;

public class AppConfig {
    private Map<String, String> config;
    private String configFilePath;
    private String outputFilePath;

    public AppConfig(Map<String, String> config) {
        this.config = Objects.requireNonNull(config, "Config map can not be null!");
    }

    public AppConfig(String configFilePath, String outputFilePath) {
        this.configFilePath = configFilePath;
        this.outputFilePath = outputFilePath;
    }

    public AppConfig load() {
        if (config.containsKey("config.file.path")) {
            this.configFilePath = config.get("config.file.path");
        }

        if (config.containsKey("output.file.path")) {
            this.outputFilePath = config.get("output.file.path");
        }

        this.config = null;
        return this;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppConfig appConfig = (AppConfig) o;
        return com.google.common.base.Objects.equal(configFilePath, appConfig.configFilePath) && com.google.common.base.Objects.equal(outputFilePath, appConfig.outputFilePath);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(configFilePath, outputFilePath);
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "configFilePath='" + configFilePath + '\'' +
                ", outputFilePath='" + outputFilePath + '\'' +
                '}';
    }
}
