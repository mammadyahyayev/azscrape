package az.my.datareport.parser;

import com.google.common.base.Objects;

public class AppConfig {
    private final String configFilePath;
    private final String outputFilePath;

    public AppConfig(String configFilePath, String outputFilePath) {
        this.configFilePath = configFilePath;
        this.outputFilePath = outputFilePath;
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
        return Objects.equal(configFilePath, appConfig.configFilePath) && Objects.equal(outputFilePath, appConfig.outputFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(configFilePath, outputFilePath);
    }
}
