package az.caspian.scrape;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebBrowserTest {
    private static WebBrowser browser;

    @BeforeAll
    static void setUp() {
        browser = new WebBrowser();
    }

    @Test
    void testOpenAndCloseWebBrowser() {
        assertTrue(browser.isOpen());

        browser.close();

        assertFalse(browser.isOpen());
    }

    @Test
    void testConnectToWebPage() {
        browser.open();

        WebPage page = browser.goTo("https://www.google.com");
        assertTrue(page.isConnected());
    }

    @Test
    void testConnectToWebPageWithDelay() {
        int delay = 3000;
        String url = "https://www.google.com";

        browser.open();

        var page = browser.goTo(url, delay);
        assertTrue(page.isConnected());
    }

    @AfterAll
    static void teardown() {
        if (browser.isOpen())
            browser.close();
    }
}