package az.my.datareport.config;

import az.my.datareport.tree.DataAST;
import az.my.datareport.tree.DataElement;
import az.my.datareport.tree.DataNode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Loads configurations for config file from different
 * sources
 */
public class ConfigLoader {

    public DataAST loadConfig(String json) {
        ObjectMapper mapper = new ObjectMapper();
        TempConfig tempConfig;

        try {
            tempConfig = mapper.readValue(json, TempConfig.class);
        } catch (JsonProcessingException e) {
            throw new ConfigurationException(e);
        }

        ConfigDataValidator validator = new ConfigDataValidator(tempConfig);
        boolean isValid = validator.validate();

        if (!isValid) {
            throw new ConfigurationException("Config file isn't valid!");
        }

        DataAST data = new DataAST();
        DataNode dataNode = new DataNode();
        dataNode.setUrl(tempConfig.getData().getUrl());

        DataElement dataElement = new DataElement();
        dataElement.setName(tempConfig.getData().getElement().getName());
        dataElement.setSelector(tempConfig.getData().getElement().getSelector());

        Set<DataElement> children = new HashSet<>();
        for (TempDataElement child : tempConfig.getData().getElement().getChildren()) {
            DataElement element = new DataElement(child.getName(), child.getSelector());
            children.add(element);
        }

        dataElement.setChildren(children);
        dataNode.setElement(dataElement);
        data.setDataNode(dataNode);

        return data;
    }

    //region Temp Classes

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TempConfig {
        @JsonProperty("description")
        private String description;

        @JsonProperty("exported_file_name")
        private String exportedFileName;

        @JsonProperty("exported_file_type")
        private String exportedFileType;

        @JsonProperty("exported_file_type_extension")
        private String exportedFileTypeExtension;

        @JsonProperty("data")
        private TempData data;

        // region Getters/Setters
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

        public TempData getData() {
            return data;
        }

        public void setData(TempData data) {
            this.data = data;
        }

        // endregion

        //region toString
        @Override
        public String toString() {
            return "DummyConfig{" +
                    "description='" + description + '\'' +
                    ", exportedFileName='" + exportedFileName + '\'' +
                    ", exportedFileType='" + exportedFileType + '\'' +
                    ", exportedFileTypeExtension='" + exportedFileTypeExtension + '\'' +
                    ", data=" + data +
                    '}';
        }
        //endregion
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TempData {
        @JsonProperty("url")
        private String url;

        @JsonProperty("element")
        private TempDataElement element;

        // region Getters/Setters
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public TempDataElement getElement() {
            return element;
        }

        public void setElement(TempDataElement element) {
            this.element = element;
        }
        // endregion

        //region toString
        @Override
        public String toString() {
            return "DummyDataElement{" +
                    "url='" + url + '\'' +
                    '}';
        }
        //endregion
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TempDataElement {
        @JsonProperty("name")
        private String name;

        @JsonProperty("selector")
        private String selector;

        @JsonProperty("children")
        private Set<TempDataElement> children = new HashSet<>();

        // region Getters/Setters
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

        public Set<TempDataElement> getChildren() {
            return children;
        }

        public void setChildren(Set<TempDataElement> children) {
            this.children = children;
        }
        // endregion

        //region toString
        @Override
        public String toString() {
            return "DummyDataElement{" +
                    ", name='" + name + '\'' +
                    ", selector='" + selector + '\'' +
                    ", children=" + children +
                    '}';
        }
        //endregion
    }

    //endregion
}
