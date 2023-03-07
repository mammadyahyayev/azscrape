package az.my.datareport.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ElementVM {
    @JsonProperty("name")
    private String name;

    @JsonProperty("selector")
    private String selector;

    @JsonProperty("sub_elements")
    private List<ElementVM> subElements;

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

    public List<ElementVM> getSubElements() {
        return subElements;
    }

    public void setSubElements(List<ElementVM> subElements) {
        this.subElements = subElements;
    }
}
