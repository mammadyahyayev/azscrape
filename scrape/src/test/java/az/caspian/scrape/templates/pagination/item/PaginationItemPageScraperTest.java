package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.ReportDataTable;
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
        var price = new DataTree<>(new DataNode("price", ".products-i__price .product-price"));

        var linkToDetailsPageNode = new DataNode("link", ".products-i__link");
        linkToDetailsPageNode.setLink(true);

        var linkToDetailsPage = new DataTree<>(linkToDetailsPageNode);

        var detailsRootNode = new DataNode("details-root", ".product-content");
        detailsRootNode.setRoot(true);

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
    @Disabled
    void test() {
//        DataNode node = new DataNode.Builder()
//                .name("")
//                .selector("")
//                .children(
//                        new Element()
//                                .name("")
//                                .selector("")
//                                .filter(FilterOp.trimValue()),
//                        new Element()
//                                .name("")
//                                .selector("")
//                                .filter(FilterOp.convertNameTo(NamingStyle.UPPERCASE)),
//                        new Element()
//                                .name("")
//                                .selector("")
//                                .filter(el -> el.getName().toLowerCase())
//                )
//                .build();
    }
}