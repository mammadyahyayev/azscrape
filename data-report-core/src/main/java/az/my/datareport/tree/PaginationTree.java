package az.my.datareport.tree;

import az.my.datareport.utils.Asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginationTree implements Tree {
    private final PageParameters pageParameters;
    private final List<DataNode> dataNodeList;

    public PaginationTree(PageParameters pageParameters) {
        this.pageParameters = pageParameters;

        dataNodeList = new ArrayList<>();
    }

    @Override
    public void addNode(DataNode node) {
        Asserts.required(node, "dataNode is required field");

        if (dataNodeList.size() == 0) {
            DataNodeLocation firstLocation = DataNodeLocation.init();
            node.setLocation(firstLocation);
            node.setRoot(true);
        }

        dataNodeList.add(node);

        if (node.hasSubNode()) {
            DataNodeLocation lastLocation = node.getLocation();
            String nextLevel = lastLocation.nextLevel();

            for (int i = 0; i < node.subNodes().size(); i++) {
                DataNode prev = i > 0 ? node.getSubNode(i - 1) : null;
                DataNode curr = node.getSubNode(i);

                if (i == 0) {
                    curr.setLocation(new DataNodeLocation(nextLevel, 0));
                    continue;
                }

                curr.setLocation(new DataNodeLocation(nextLevel, prev.getLocation().nextOrder()));
            }
        }
    }

    /**
     * Gives list of nodes in tree. Returned list shouldn't be
     * modified, otherwise it will throw {@link UnsupportedOperationException}
     *
     * @return list of nodes in tree
     */
    @Override
    public List<DataNode> nodes() {
        return Collections.unmodifiableList(this.dataNodeList);
    }

}
