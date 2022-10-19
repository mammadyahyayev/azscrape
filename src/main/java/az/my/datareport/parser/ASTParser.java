package az.my.datareport.parser;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

public class ASTParser {

    DataAST parseJsonNode(JsonNode jsonNode) {
        DataAST dataAST = new DataAST();
        for (Iterator<JsonNode> it = jsonNode.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            String name = node.get("name").asText();
            Assert.required(name, "name");

            String selector = node.get("selector").asText();
            Assert.required(selector, "selector");

            DataElement dataElement = new DataElement(name, selector);
            dataAST.addChildDataElement(dataElement);
        }

        return dataAST;
    }

}
