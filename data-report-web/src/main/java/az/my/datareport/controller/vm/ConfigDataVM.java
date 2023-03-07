package az.my.datareport.controller.vm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigDataVM {

    @JsonProperty("url")
    private String url;

    @JsonProperty("elements")
    private List<ElementVM> elements;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ElementVM> getElements() {
        return elements;
    }

    public void setElements(List<ElementVM> elements) {
        this.elements = elements;
    }
}
