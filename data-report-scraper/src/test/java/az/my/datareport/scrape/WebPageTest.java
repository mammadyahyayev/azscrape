package az.my.datareport.scrape;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebPageTest {

    private static final String URL = "https://github.com/search?q=java";

    @Test
    void testConnect_whenURLGiven_returnTrue() {
        WebPage page = new WebPage(URL);
        page.connect();
        assertTrue(page.isConnected());
    }

    @Test
    void testFetchElementsAsText_whenInvalidSelectorGiven_returnEmptyList() {
        WebPage page = new WebPage(URL);
        page.connect();
        List<String> strings = page.fetchElementsAsText("invalid selector");
        assertEquals(0, strings.size());
    }

    @Test
    void testFetchElementsAsText_whenCorrectSelectorGiven_returnScrapedData() {
        WebPage page = new WebPage(URL);
        page.connect();
        List<String> strings = page.fetchElementsAsText(".repo-list-item  .v-align-middle");
        assertNotEquals(0, strings.size());
    }

}