package az.my.datareport.scrape;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebPageTest {

    private static final String URL = "https://github.com/search?q=java";

    @Test
    void should_return_true_after_connected_to_web_page() {
        WebPage page = new WebPage(URL);
        page.connect();
        assertTrue(page.isConnected());
    }

    @Test
    void should_return_empty_list_for_given_invalid_selector() {
        WebPage page = new WebPage(URL);
        List<String> strings = page.fetchElements("invalid selector");
        assertEquals(0, strings.size());
    }

    @Test
    void should_not_equal_to_empty_list_for_true_selector() {
        WebPage page = new WebPage(URL);
        List<String> strings = page.fetchElements(".repo-list-item  .v-align-middle");
        assertNotEquals(0, strings.size());
    }

}