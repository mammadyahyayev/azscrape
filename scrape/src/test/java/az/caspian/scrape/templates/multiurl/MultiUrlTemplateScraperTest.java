package az.caspian.scrape.templates.multiurl;

import az.caspian.core.tree.*;
import az.caspian.core.tree.node.DataNode;
import az.caspian.core.tree.node.KeyValueDataNode;
import az.caspian.core.tree.node.ListNode;
import az.caspian.core.tree.node.Node;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MultiUrlTemplateScraperTest {

  @Test
  void testCollectingDataFromSameStructuredWebsiteUrls() {
    var urls = Set.of(
      "https://turbo.az/autos?page=1",
      "https://turbo.az/autos?page=2",
      "https://turbo.az/autos?page=3"
    );

    var templateParameters = new MultiUrlTemplateParameters.Builder()
      .urls(urls)
      .urlSource(Path.of("src/test/resources/urls.txt"))
      .delayBetweenUrls(1, TimeUnit.SECONDS)
      .failFast(false)
      .build();

    var link = new ListNode("link", ".products-i__link");
    var carNode = new DataNode("car", ".product-title");
    var price = new DataNode("price", ".product-price > div:first-child");
    var advertisementId = new DataNode("advertisement number", ".product-actions__id");
    var description = new DataNode("description", ".product-description__content");
    var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
    var viewCount = new DataNode("view count", ".product-statistics__i:last-child");

    var propertyWrapper = new ListNode("wrapper", ".product-properties__i");
    var properties =
      new KeyValueDataNode(".product-properties__i-name", ".product-properties__i-value");
    propertyWrapper.addChild(properties);

    link.addChild(carNode);
    link.addChild(price);
    link.addChild(advertisementId);
    link.addChild(description);
    link.addChild(updateTime);
    link.addChild(viewCount);
    link.addChild(propertyWrapper);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(link);

    var template = new MultiUrlTemplate(templateParameters, tree);
    var scraper = new MultiUrlTemplateScraper();
    DataTable table = scraper.scrape(template);
    assertNotNull(table);
  }

}