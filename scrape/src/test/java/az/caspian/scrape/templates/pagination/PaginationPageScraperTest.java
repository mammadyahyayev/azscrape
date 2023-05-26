package az.caspian.scrape.templates.pagination;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.Scraper;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaginationPageScraperTest {

    @Test
    void testPaginationPageScraper() {
        var pageParameters = new PageParameters.Builder()
                .url("https://github.com/search?p={pageNum}")
                .pageRange(0, 3)
                .queryParam("q", "java")
                .queryParam("type", "Repositories")
                .delayBetweenPages(10000)
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
        ScrapeErrorCallback mockCallback = mock(ScrapeErrorCallback.class);
        PaginationTemplate mockPaginationTemplate = mock(PaginationTemplate.class);

        PaginationPageScraper scraper = new PaginationPageScraper(mockCallback);
        when(mockPaginationTemplate.getPageParameters()).thenThrow(new RuntimeException("Page parameters error"));

        assertThrows(RuntimeException.class, () -> scraper.scrape(mockPaginationTemplate));
        verify(mockCallback).handle(eq("Page parameters error"), any(ReportDataTable.class));
    }

}