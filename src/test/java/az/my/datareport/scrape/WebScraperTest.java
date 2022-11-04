package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.ast.DataNode;
import az.my.datareport.model.ReportData;
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
        DataElement dataElement = new DataElement("title", ".repo-list-item  .v-align-middle");
        DataElement dataElement2 = new DataElement("description", ".repo-list-item  .mb-1");

        DataNode dataNode = new DataNode();
        dataNode.setUrl(url);
        dataNode.setElements(List.of(dataElement, dataElement2));

        dataAST = new DataAST();
        dataAST.setDataNodes(List.of(dataNode));
    }

    @Test
    void collected_data_size_should_be_greater_than_zero() {
        Scraper scraper = new WebScraper();
        List<ReportData> reportDataList = scraper.scrape(dataAST);

        assertNotNull(reportDataList);
        reportDataList.forEach(reportData -> {
            assertTrue(reportData.getReportDataElements().size() > 0);

            reportData.getReportDataElements().forEach((element) -> {
                assertTrue(element.values().size() > 0);
            });
        });
    }

}