package az.my.datareport.controller.vm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigFileVM {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_type")
    private String fileType;

    @JsonProperty("file_extension")
    private String fileExtension;

    @JsonProperty("description")
    private String description;

    @JsonProperty("data_parts")
    private List<ConfigDataVM> dataParts;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ConfigDataVM> getDataParts() {
        return dataParts;
    }

    public void setDataParts(List<ConfigDataVM> dataParts) {
        this.dataParts = dataParts;
    }
}
