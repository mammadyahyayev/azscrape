package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.model.DataColumn;
import az.caspian.core.tree.*;
import az.caspian.scrape.templates.pagination.PageParameters;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        var link = new DataNode("link", ".products-i__link");
        link.setLink(true);

        DataTree<Node> tree = new DataTree<>(link);

        var carNode = new DataNode("car", ".product-title", true);
        var price = new DataNode("price", ".product-price > div:first-child");
        var advertisementId = new DataNode("advertisement number", ".product-actions__id");
        var description = new DataNode("description", ".product-description__content");
        var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
        var viewCount = new DataNode("view count", ".product-statistics__i:last-child");
        var properties = new KeyValueDataNode(".product-properties__i",
                ".product-properties__i-name",
                ".product-properties__i-value"
        );

        tree.addChild(carNode, link);
        tree.addChild(price, link);
        tree.addChild(advertisementId, link);
        tree.addChild(description, link);
        tree.addChild(updateTime, link);
        tree.addChild(viewCount, link);
        tree.addChild(properties, link);

        PaginationItemVisitorTemplate template = new PaginationItemVisitorTemplate(pageParameters, tree);

        PaginationItemVisitorScraper scraper = new PaginationItemVisitorScraper();
        ReportDataTable table = scraper.scrape(template);

        assertNotNull(table);
    }
}
