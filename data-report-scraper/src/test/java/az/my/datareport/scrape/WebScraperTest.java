package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.DataNodeAttribute;
import az.my.datareport.tree.Tree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO: Extract variables from method, and make them globally available inside the class
// then write different test methods for them, fetch data inside @BeforeAll method
class WebScraperTest {

    private final String URL = "https://github.com/search?q=java";

    @Test
    void testScrape_whenDataGivenAsTree_returnScrapedDataElements() {
        Tree tree = new Tree();

        DataNode repoItem = new DataNode(new DataNodeAttribute("repoItem", ".repo-list-item"));
        DataNode node1 = new DataNode(new DataNodeAttribute("title", ".v-align-middle"));
        DataNode node2 = new DataNode(new DataNodeAttribute("description", ".mb-1"));

        repoItem.addSubNode(node1);
        repoItem.addSubNode(node2);

        tree.addNode(repoItem);

        Scraper scraper = new WebScraper();
        ReportData reportData = scraper.scrape(URL, tree);

        assertNotNull(reportData);
        assertTrue(reportData.getReportParentElements().size() > 0);
        reportData.getReportParentElements().forEach(parent -> {
            assertTrue(parent.getReportDataElements().size() > 0);
        });
    }

}