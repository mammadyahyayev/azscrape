package az.my.datareport.tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// https://github.com/search?q=java&type=Repositories&p=0
class PaginationTest {

    private static final String URL = "https://github.com/search";

    private static PageParameters pageParameters = null;

    @BeforeAll
    static void setUp() {
        pageParameters = new PageParameters();
        pageParameters.setPageUrl(URL);
        pageParameters.setMinPage(0);
        pageParameters.setMaxPage(0);
        pageParameters.setDelayBetweenPages(10000);
        pageParameters.addQueryParameter(new QueryParameter("q", "java", false));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories", false));
        pageParameters.addQueryParameter(new QueryParameter("p", "0", true));
    }

    /*@Test
    void testPaginationTree() {
        Pagination tree = new Pagination(pageParameters);

        var parent = new DataNode(new DataNodeAttribute("Parent", ".repo-list-item"));
        tree.addNode(parent);

        parent.addSubNode(new DataNode(new DataNodeAttribute("Child 1", ".child-1")));
        parent.addSubNode(new DataNode(new DataNodeAttribute("Child 2", ".child-2")));

        assertTrue(parent.isRoot());
        assertTrue(parent.hasSubNode());
        assertEquals(2, parent.subNodes().size());
    }

    @Test
    void testPaginationTreeNodeLocations() {
        Pagination tree = new Pagination(pageParameters);

        var parent = new DataNode(new DataNodeAttribute("Parent", ".repo-list-item"));

        var child1 = new DataNode(new DataNodeAttribute("Child 1", ".child-1"));
        parent.addSubNode(child1);

        var child2 = new DataNode(new DataNodeAttribute("Child 2", ".child-2"));
        parent.addSubNode(child2);

        tree.addNode(parent);

        assertEquals(new DataNodeLocation("A", 0), parent.getLocation());
        assertEquals(new DataNodeLocation("B", 0), child1.getLocation());
        assertEquals(new DataNodeLocation("B", 1), child2.getLocation());
    }
*/

}