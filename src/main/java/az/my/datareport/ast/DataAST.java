package az.my.datareport.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataAST {
    private final List<DataElement> elements = new ArrayList<>();

    public List<DataElement> getElements() {
        return new ArrayList<>(elements);
    }

    public void addChildDataElement(final DataElement element) {
        Objects.requireNonNull(element);

        elements.add(element);
    }
}
