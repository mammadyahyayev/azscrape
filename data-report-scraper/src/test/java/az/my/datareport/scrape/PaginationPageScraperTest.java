package az.my.datareport.scrape;

import az.my.datareport.tree.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginationPageScraperTest {

    @Test
    void testPaginationPageScraper() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://github.com/search");
        pageParameters.setMinPage(0);
        pageParameters.setMaxPage(10);
        pageParameters.addQueryParameter(new QueryParameter("q", "java", false));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories", false));
        pageParameters.addQueryParameter(new QueryParameter("p", "0", true));
        pageParameters.setDelayBetweenPages(10000);

        PaginationTree tree = new PaginationTree(pageParameters);

        DataNode repoItem = new DataNode(new DataNodeAttribute("repoItem", ".repo-list-item"));
        DataNode title = new DataNode(new DataNodeAttribute("title", ".v-align-middle"));
        DataNode description = new DataNode(new DataNodeAttribute("description", ".mb-1"));

        repoItem.addSubNode(title);
        repoItem.addSubNode(description);

        tree.addNode(repoItem);

        Scraper scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        assertTrue(table.rows().size() > 0);
    }

}