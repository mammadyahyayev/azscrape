package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.*;
import az.caspian.scrape.templates.pagination.PageParameters;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaginationItemVisitorScraperTest {

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testPaginationItemPageScraper() {
        var pageParameters = new PageParameters.Builder()
                .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
                .pageNum(1)
                .delayBetweenPages(3000)
                .build();

        var linkNode = new LinkNode("link", ".products-i__link");
        var carNode = new DataNode("car", ".product-title");
        var price = new DataNode("price", ".product-price > div:first-child");
        var advertisementId = new DataNode("advertisement number", ".product-actions__id");
        var description = new DataNode("description", ".product-description__content");
        var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
        var viewCount = new DataNode("view count", ".product-statistics__i:last-child");

        var properties = new ParentNode("key value wrapper", ".product-properties__i");
        var propertiesKeyValue = new KeyValueDataNode(
                ".product-properties__i-name",
                ".product-properties__i-value"
        );
        properties.addChild(propertiesKeyValue);

        DataTree<Node> tree = new DataTree<>(linkNode);
        tree.addNode(carNode);
        tree.addNode(price);
        tree.addNode(advertisementId);
        tree.addNode(description);
        tree.addNode(updateTime);
        tree.addNode(viewCount);
        tree.addNode(properties);

        PaginationItemVisitorTemplate template = new PaginationItemVisitorTemplate(pageParameters, tree);

        PaginationItemVisitorScraper scraper = new PaginationItemVisitorScraper();
        ReportDataTable table = scraper.scrape(template);

        assertNotNull(table);
    }
}
