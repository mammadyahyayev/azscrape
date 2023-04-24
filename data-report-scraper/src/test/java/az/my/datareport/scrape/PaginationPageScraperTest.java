package az.my.datareport.scrape;

import az.my.datareport.tree.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginationPageScraperTest {

    @Test
    void testPaginationPageScraper() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://github.com/search?p={pageNumber}");
        pageParameters.setMinPage(0);
        pageParameters.setMaxPage(3);
        pageParameters.addQueryParameter(new QueryParameter("q", "java", false));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories", false));
        pageParameters.setDelayBetweenPages(10000);
        pageParameters.build();


        DataTree<DataNode> repoItem = new DataTree<>(new DataNode("repoItem", ".repo-list-item"));
        DataTree<DataNode> title = new DataTree<>(new DataNode("title", ".v-align-middle"));
        DataTree<DataNode> description = new DataTree<>(new DataNode("description", ".mb-1"));

        repoItem.addSubNode(title);
        repoItem.addSubNode(description);

        Pagination tree = new Pagination(pageParameters, repoItem);

        Scraper<Pagination> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        assertTrue(table.rows().size() > 0);
    }

}