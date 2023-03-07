package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO: Extract variables from method, and make them globally available inside the class
// then write different test methods for them, fetch data inside @BeforeAll method
class WebScraperTest {

    private final String url = "https://github.com/search?q=java";
    private DataAST dataAST;

    @BeforeEach
    public void setUp() {
        DataElement parent = new DataElement();
        parent.setSelector(".repo-list-item");

        DataElement dataElement = new DataElement("title", ".v-align-middle");
        DataElement dataElement2 = new DataElement("description", ".mb-1");
        parent.setChildren(Set.of(dataElement, dataElement2));

        DataNode dataNode = new DataNode();
        dataNode.setUrl(url);
        dataNode.setElement(parent);

        dataAST = new DataAST();
        dataAST.setDataNode(dataNode);
    }

    @Test
    void testScrape_whenDataGivenAsAST_returnScrapedDataElements() {
        Scraper scraper = new WebScraper();
        ReportData reportData = scraper.scrape(dataAST);

        assertNotNull(reportData);
        assertTrue(reportData.getReportParentElements().size() > 0);
        reportData.getReportParentElements().forEach(parent -> {
            assertTrue(parent.getReportDataElements().size() > 0);
        });
    }

    @Test
    void testScrape_whenDataGivenAsTree_returnScrapedDataElements() {
        Tree tree = new Tree();

        TempDataNode repoItem = new TempDataNode(new DataNodeAttribute("repoItem", ".repo-list-item"));
        TempDataNode node1 = new TempDataNode(new DataNodeAttribute("title", ".v-align-middle"));
        TempDataNode node2 = new TempDataNode(new DataNodeAttribute("description", ".mb-1"));

        repoItem.addSubNode(node1);
        repoItem.addSubNode(node2);

        tree.addNode(repoItem);

        Scraper scraper = new WebScraper();
        ReportData reportData = scraper.scrape("https://github.com/search?q=java", tree);

        assertNotNull(reportData);
        assertTrue(reportData.getReportParentElements().size() > 0);
        reportData.getReportParentElements().forEach(parent -> {
            assertTrue(parent.getReportDataElements().size() > 0);
        });
    }

}