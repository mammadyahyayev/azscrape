package az.my.datareport.exporter.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.scrape.Scraper;
import az.my.datareport.scrape.WebScraper;
import az.my.datareport.tree.DataAST;
import az.my.datareport.tree.DataElement;
import az.my.datareport.tree.DataNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        parent.setChildren(List.of(dataElement, dataElement2));

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

}