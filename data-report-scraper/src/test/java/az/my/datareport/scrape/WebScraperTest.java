package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO: Extract variables from method, and make them globally available inside the class
// then write different test methods for them, fetch data inside @BeforeAll method
class WebScraperTest {

    private final String URL = "https://github.com/search?q=java";

    @Test
    void testScrape_whenDataGivenAsTree_returnScrapedDataElements() {
        AbstractTree abstractTree = new AbstractTree();

        DataNode repoItem = new DataNode(new DataNodeAttribute("repoItem", ".repo-list-item"));
        DataNode node1 = new DataNode(new DataNodeAttribute("title", ".v-align-middle"));
        DataNode node2 = new DataNode(new DataNodeAttribute("description", ".mb-1"));

        repoItem.addSubNode(node1);
        repoItem.addSubNode(node2);

        abstractTree.addNode(repoItem);

        Scraper scraper = new WebScraper();
        ReportData reportData = scraper.scrape(URL, abstractTree);

        assertNotNull(reportData);
        assertTrue(reportData.getReportParentElements().size() > 0);
        reportData.getReportParentElements().forEach(parent -> {
            assertTrue(parent.getReportDataElements().size() > 0);
        });
    }


    @Test
    void test() throws InterruptedException {
        List<List<WebElement>> elements = new ArrayList<>();

        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://github.com/search?q=java&type=Repositories");
        pageParameters.setQueryParam("p");
        pageParameters.setMinPage(0);
        pageParameters.setMaxPage(15);
        pageParameters.setDelayBetweenPages(10000);

        PaginationTree tree = new PaginationTree(pageParameters);
        DataNode repoItem = new DataNode(new DataNodeAttribute("repoItem", ".repo-list-item"));
        DataNode node1 = new DataNode(new DataNodeAttribute("title", ".v-align-middle"));
        DataNode node2 = new DataNode(new DataNodeAttribute("description", ".mb-1"));

        repoItem.addSubNode(node1);
        repoItem.addSubNode(node2);

        tree.addNode(repoItem);

        for (int i = pageParameters.getMinPage(); i < pageParameters.getMaxPage(); i++) {
            String url = pageParameters.getPageUrl() + "&" + pageParameters.getQueryParam() + "=" + i;
            WebPage page = new WebPage(url, true);
            page.connect();

            Thread.sleep(pageParameters.getDelayBetweenPages());

            DataNode node = tree.nodes().get(0);
            List<WebElement> webElements = page.fetchWebElements(node.getAttribute().getSelector());
            elements.add(webElements);
        }

        assertTrue(elements.size() > 5);
    }
}