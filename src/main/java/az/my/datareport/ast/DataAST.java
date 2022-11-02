package az.my.datareport.ast;

import java.util.ArrayList;
import java.util.List;

public class DataAST {
    private List<DataNode> dataNodes = new ArrayList<>();

    public List<DataNode> getDataNodes() {
        return new ArrayList<>(dataNodes);
    }

    public void setDataNodes(List<DataNode> dataNodes) {
        this.dataNodes = dataNodes;
    }
}
