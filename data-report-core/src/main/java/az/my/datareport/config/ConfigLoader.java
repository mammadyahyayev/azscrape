package az.my.datareport.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Loads configurations for config file from different
 * sources
 */
public class ConfigLoader {

    //TODO: json string doesn't contain title field
    public boolean loadConfig(String json) {
        ObjectMapper mapper = new ObjectMapper();
        DummyConfig dummyConfig;

        try {
            dummyConfig = mapper.readValue(json, DummyConfig.class);
        } catch (JsonProcessingException e) {
            throw new ConfigurationException(e);
        }

        System.out.println(dummyConfig.getExportedFileName());
        /*
           TODO: check required fields, if there is a problem throw an exception.
            Check their values.
            If everything is okay return true and wait for the action from user (generate report)
        */

        return false;
    }

    static class DummyConfig {
        private String title;
        private String description;
        private String exportedFileName;
        private String exportedFileType;
        private String exportedFileTypeExtension;
        private DummyDataElement data;

        // region Getters/Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getExportedFileName() {
            return exportedFileName;
        }

        public void setExportedFileName(String exportedFileName) {
            this.exportedFileName = exportedFileName;
        }

        public String getExportedFileType() {
            return exportedFileType;
        }

        public void setExportedFileType(String exportedFileType) {
            this.exportedFileType = exportedFileType;
        }

        public String getExportedFileTypeExtension() {
            return exportedFileTypeExtension;
        }

        public void setExportedFileTypeExtension(String exportedFileTypeExtension) {
            this.exportedFileTypeExtension = exportedFileTypeExtension;
        }

        public DummyDataElement getData() {
            return data;
        }

        public void setData(DummyDataElement data) {
            this.data = data;
        }

        // endregion
    }

    static class DummyDataElement {
        private String url;
        private String name;
        private String selector;
        private Set<DummyDataElement> children = new HashSet<>();

        // region Getters/Setters
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSelector() {
            return selector;
        }

        public void setSelector(String selector) {
            this.selector = selector;
        }

        public Set<DummyDataElement> getChildren() {
            return children;
        }

        public void setChildren(Set<DummyDataElement> children) {
            this.children = children;
        }
        // endregion
    }

}
