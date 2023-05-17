package az.my.datareport.scrape.templates.scroll;

import az.my.datareport.scrape.Scraper;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.DataTree;
import az.my.datareport.tree.ReportDataTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScrollablePageScraperTest {

    @Test
    void testPaginationPageScraper() {
        var pageParameters = new ScrollablePageParameters.Builder()
                .url("https://turbo.az/")
                .build();

        var repoItem = new DataTree<>(new DataNode("wrapper", ".products-i"));
        var car = new DataTree<>(new DataNode("car", ".products-i__name"));
        var price = new DataTree<>(new DataNode("price", ".products-i__price .product-price"));
        var details = new DataTree<>(new DataNode("details", ".products-i__attributes"));

        repoItem.addSubNode(car);
        repoItem.addSubNode(price);
        repoItem.addSubNode(details);

        ScrollablePageTemplate tree = new ScrollablePageTemplate(pageParameters, repoItem);

        Scraper<ScrollablePageTemplate> scraper = new ScrollablePageScraper();
        ReportDataTable table = scraper.scrape(tree);

        assertTrue(table.rows().size() > 0);
    }


}