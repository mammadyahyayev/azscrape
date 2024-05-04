package az.caspian.scrape.templates.pagination;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.DataTable;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.node.DataNode;
import az.caspian.core.tree.node.ListNode;
import az.caspian.core.tree.node.Node;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import az.caspian.scrape.templates.Scraper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaginationPageScraperTest {

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testPaginationPageScraper() {
    var pageParameters =
      new PageParameters.Builder()
        .url("https://github.com/search?p={pageNum}")
        .pageRange(0, 2)
        .queryParam("q", "java")
        .queryParam("type", "Repositories")
        .delayBetweenPages(3000)
        .build();

    var repoItem = new ListNode("repoItem", ".Box-sc-g0xbh4-0 .bItZsX");
    var title = new DataNode("title", ".search-title");
    var description = new DataNode("description", ".Text-sc-17v1xeu-0");

    repoItem.addChild(title);
    repoItem.addChild(description);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(repoItem);

    PaginationTemplate template = new PaginationTemplate(pageParameters, tree);

    Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
    DataTable table = scraper.scrape(template);

    assertNotNull(table);
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testCallbackCalledWhenExceptionHappens() {
    var mockCallback = mock(ScrapeErrorCallback.class);
    var mockPaginationTemplate = mock(PaginationTemplate.class);

    var scraper = new PaginationPageScraper(mockCallback);

    var exceptionMessageCaptor = ArgumentCaptor.forClass(String.class);
    when(mockPaginationTemplate.getPageParameters())
      .thenThrow(new RuntimeException("Error happened"));

    var runtimeException =
      assertThrows(RuntimeException.class, () -> scraper.scrape(mockPaginationTemplate));
    verify(mockCallback).handle(exceptionMessageCaptor.capture(), any(DataTable.class));
    assertEquals(runtimeException.getMessage(), exceptionMessageCaptor.getValue());
  }
}
