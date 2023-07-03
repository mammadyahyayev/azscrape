package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.*;
import az.caspian.scrape.templates.Scraper;
import az.caspian.scrape.templates.pagination.PageParameters;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaginationItemPageScraperTest {

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testPaginationItemPageScraper() {
        var pageParameters = new PageParameters.Builder()
                .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
                .pageNum(1)
                .delayBetweenPages(3000)
                .build();


        var repoItem = new DataTree<>(new DataNode("wrapper", ".products-i"));
        var car = new DataTree<>(new DataNode("car", ".products-i__name", true));
        var price = new DataTree<>(new DataNode("price", ".product-price .product-price__i"));

        var linkToDetailsPageNode = new DataNode("link", ".products-i__link");
        linkToDetailsPageNode.setLink(true);

        var linkToDetailsPage = new DataTree<>(linkToDetailsPageNode);

        var detailsRootNode = new DataNode("details-root", ".product-content");
        detailsRootNode.setParent(true);

        var detailsRoot = new DataTree<>(detailsRootNode);

        var statistics = new DataTree<>(new DataNode("statistics", ".product-statistics__i-text"));
        var properties = new DataTree<>(new DataNode("properties", ".product-properties__i"));
        var description = new DataTree<>(new DataNode("description", ".product-description__content"));
        var advertisementId = new DataTree<>(new DataNode("advertisement number", ".product-actions__id"));

        repoItem.addSubNode(linkToDetailsPage);
        repoItem.addSubNode(car);
        repoItem.addSubNode(price);
        repoItem.addSubNode(detailsRoot);
        detailsRoot.addSubNode(statistics);
        detailsRoot.addSubNode(properties);
        detailsRoot.addSubNode(description);
        detailsRoot.addSubNode(advertisementId);

        PaginationItemTemplate tree = new PaginationItemTemplate(pageParameters, repoItem);

        Scraper<PaginationItemTemplate> scraper = new PaginationItemPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        assertNotNull(table);
    }

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void test() {
        var pageParameters = new PageParameters.Builder()
                .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
                .pageNum(1)
                .delayBetweenPages(3000)
                .build();

        var link = new DataNode("link", ".products-i__link");
        link.setLink(true);

        DataTree<DataNode> tree = new DataTree<>(link);

        var carNode = new DataNode("car", ".product-title", true);
        var price = new DataNode("price", ".product-price > div:first-child");
        var advertisementId = new DataNode("advertisement number", ".product-actions__id");
        var description = new DataNode("description", ".product-description__content");
        var updateTime = new DataNode("update time",".product-statistics__i:first-child");
        var viewCount = new DataNode("view count",".product-statistics__i:last-child");
        var properties = new DataNode("properties", ".product-properties");
        properties.setKeyValuePair(true);

        tree.addChild(carNode, link);
        tree.addChild(price, link);
        tree.addChild(advertisementId, link);
        tree.addChild(description, link);
        tree.addChild(updateTime, link);
        tree.addChild(viewCount, link);
        tree.addChild(properties, link);

        PaginationItemTemplate template = new PaginationItemTemplate(pageParameters, tree);

        PaginationItemPageScraper scraper = new PaginationItemPageScraper();
        ReportDataTable table = scraper.scrape(template);

        assertNotNull(table);
    }
}
