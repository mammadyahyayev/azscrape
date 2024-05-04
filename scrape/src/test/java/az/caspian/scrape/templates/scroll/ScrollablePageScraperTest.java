package az.caspian.scrape.templates.scroll;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.DataTable;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.node.DataNode;
import az.caspian.core.tree.node.ListNode;
import az.caspian.core.tree.node.Node;
import az.caspian.scrape.templates.Scraper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ScrollablePageScraperTest {

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testPaginationPageScraper() {
    var pageParameters = new ScrollablePageParameters.Builder().url("https://turbo.az/").build();

    var listNode = new ListNode("wrapper", ".products-i");
    var car = new DataNode("car", ".products-i__name");
    var price = new DataNode("price", ".products-i__price .product-price");
    var details = new DataNode("details", ".products-i__attributes");

    listNode.addChild(car);
    listNode.addChild(price);
    listNode.addChild(details);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    ScrollablePageTemplate template = new ScrollablePageTemplate(pageParameters, tree);

    Scraper<ScrollablePageTemplate> scraper = new ScrollablePageScraper();
    DataTable table = scraper.scrape(template);

    assertFalse(table.rows().isEmpty());
  }
}
