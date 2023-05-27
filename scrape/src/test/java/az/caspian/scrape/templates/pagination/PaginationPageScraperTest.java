package az.caspian.scrape.templates.pagination;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import az.caspian.scrape.templates.Scraper;
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
    void testPaginationPageScraper() {
        var pageParameters = new PageParameters.Builder()
                .url("https://github.com/search?p={pageNum}")
                .pageRange(0, 2)
                .queryParam("q", "java")
                .queryParam("type", "Repositories")
                .delayBetweenPages(3000)
                .build();


        DataTree<DataNode> repoItem = new DataTree<>(new DataNode("repoItem", ".repo-list-item"));
        DataTree<DataNode> title = new DataTree<>(new DataNode("title", ".v-align-middle"));
        DataTree<DataNode> description = new DataTree<>(new DataNode("description", ".mb-1"));

        repoItem.addSubNode(title);
        repoItem.addSubNode(description);

        PaginationTemplate tree = new PaginationTemplate(pageParameters, repoItem);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        assertTrue(table.rows().size() > 0);
    }

    @Test
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