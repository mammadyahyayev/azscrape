package az.my.datareport.parser;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class ASTParser {

    DataAST parseJSON(List<JsonNode> nodes) {
        List<DataElement> elements = new ArrayList<>();
        DataAST dataAST = new DataAST();
        for (JsonNode node : nodes) {
            String name = node.get("name").asText();
            Assert.required(name, "name");

            String selector = node.get("selector").asText();
            Assert.required(selector, "selector");

            DataElement dataElement = new DataElement(name, selector);
            elements.add(dataElement);
        }

        dataAST.setElements(elements);

        return dataAST;
    }

}
