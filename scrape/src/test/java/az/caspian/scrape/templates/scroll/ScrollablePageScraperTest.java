package az.caspian.scrape.templates.scroll;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.templates.Scraper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ScrollablePageScraperTest {

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testPaginationPageScraper() {
        var pageParameters = new ScrollablePageParameters.Builder()
                .url("https://turbo.az/")
                .build();

        var repoItem = new DataNode("wrapper", ".products-i");
        var car = new DataNode("car", ".products-i__name");
        var price = new DataNode("price", ".products-i__price .product-price");
        var details = new DataNode("details", ".products-i__attributes");

        DataTree<Node> tree = new DataTree<>(repoItem);
        tree.addNode(car);
        tree.addNode(price);
        tree.addNode(details);

        ScrollablePageTemplate template = new ScrollablePageTemplate(pageParameters, tree);

        Scraper<ScrollablePageTemplate> scraper = new ScrollablePageScraper();
        ReportDataTable table = scraper.scrape(template);

        assertFalse(table.rows().isEmpty());
    }


}