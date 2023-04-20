package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.DataNodeAttribute;
import az.my.datareport.tree.PageParameters;
import az.my.datareport.tree.PaginationTree;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginationPageScraperTest {

    @Test
    void testPaginationPageScraper() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://github.com/search?q=java&type=Repositories");
        pageParameters.setMinPage(0);
        pageParameters.setMaxPage(10);
        pageParameters.setQueryParam("p");
        pageParameters.setDelayBetweenPages(10000);

        PaginationTree tree = new PaginationTree(pageParameters);

        DataNode repoItem = new DataNode(new DataNodeAttribute("repoItem", ".repo-list-item"));
        DataNode title = new DataNode(new DataNodeAttribute("title", ".v-align-middle"));
        DataNode description = new DataNode(new DataNodeAttribute("description", ".mb-1"));

        repoItem.addSubNode(title);
        repoItem.addSubNode(description);

        tree.addNode(repoItem);

        Scraper scraper = new PaginationPageScraper();
        List<ReportData> reportDataList = scraper.scrape(tree);

        assertTrue(reportDataList.size() > 1);
    }

}