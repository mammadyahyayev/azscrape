package az.caspian.scrape.templates.pagination;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import az.caspian.scrape.templates.Scraper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaginationPageScraperTest {

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testPaginationPageScraper() {
        var pageParameters = new PageParameters.Builder()
                .url("https://github.com/search?p={pageNum}")
                .pageRange(0, 2)
                .queryParam("q", "java")
                .queryParam("type", "Repositories")
                .delayBetweenPages(3000)
                .build();


        var repoItem = new DataNode("repoItem", ".repo-list-item");
        var title = new DataNode("title", ".v-align-middle");
        var description = new DataNode("description", ".mb-1");

        DataTree<Node> tree = new DataTree<>(repoItem);
        tree.addNode(title);
        tree.addNode(description);

        PaginationTemplate template = new PaginationTemplate(pageParameters, tree);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        scraper.scrape(template);
    }

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testCallbackCalledWhenExceptionHappens() {
        var mockCallback = mock(ScrapeErrorCallback.class);
        var mockPaginationTemplate = mock(PaginationTemplate.class);

        var scraper = new PaginationPageScraper(mockCallback);

        var exceptionMessageCaptor = ArgumentCaptor.forClass(String.class);
        when(mockPaginationTemplate.getPageParameters()).thenThrow(new RuntimeException("Error happened"));

        var runtimeException = assertThrows(RuntimeException.class, () -> scraper.scrape(mockPaginationTemplate));
        verify(mockCallback).handle(exceptionMessageCaptor.capture(), any(ReportDataTable.class));
        assertEquals(runtimeException.getMessage(), exceptionMessageCaptor.getValue());
    }

}