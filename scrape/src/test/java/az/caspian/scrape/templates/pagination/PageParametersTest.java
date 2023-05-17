package az.caspian.scrape.templates.pagination;

import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageParametersTest {

    @Test
    void testWithoutPageSpecifier() {
        String url = "https://www.example.com/products?page";
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> new PageParameters.Builder()
                        .url(url)
                        .build()
        );

        int begin = url.indexOf('{');
        int end = url.indexOf("}");

        assertEquals(
                format("Page specifier isn't configured correctly!, begin=%d, end=%d", begin, end),
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testWithoutEndingWrapperPageSpecifier() {
        String url = "https://www.example.com/products?page={";
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> new PageParameters.Builder()
                        .url(url)
                        .build()
        );

        int begin = url.indexOf('{');
        int end = url.indexOf("}");

        assertEquals(
                format("Page specifier isn't configured correctly!, begin=%d, end=%d", begin, end),
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testWithoutBeginningWrapperPageSpecifier() {
        String url = "https://www.example.com/products?page=}";

        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> new PageParameters.Builder()
                        .url(url)
                        .build()
        );

        int begin = url.indexOf('{');
        int end = url.indexOf("}");

        assertEquals(
                format("Page specifier isn't configured correctly!, begin=%d, end=%d", begin, end),
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testWithoutPageSpecifierKey() {
        String url = "https://www.example.com/products?page={}";
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class,
                () -> new PageParameters.Builder()
                        .url(url)
                        .build()
        );

        int begin = url.indexOf('{');
        int pageKey = url.indexOf("pageNum");
        int end = url.indexOf("}");

        assertEquals(
                format("Page specifier is in wrong place!, begin=%d, pageKey=%d, end=%d", begin, pageKey, end),
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testQueryParamSpecifierGiven() {
        var pageParameters = new PageParameters.Builder()
                .url("https://www.example.com/products?page={pageNum}")
                .build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products?page=4", pageUrl);
    }

    @Test
    void testQueryParamSpecifierGivenWithQueryParameters() {
        var pageParameters = new PageParameters.Builder()
                .url("https://www.example.com/products?page={pageNum}")
                .queryParam("q", "java")
                .queryParam("type", "Repositories")
                .build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products?page=4&q=java&type=Repositories", pageUrl);
    }

    @Test
    void testHashFragmentSpecifierGiven() {
        var pageParameters = new PageParameters.Builder()
                .url("https://www.example.com/products#page={pageNum}")
                .build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products#page=4", pageUrl);
    }

    @Test
    void testHashFragmentSpecifierGivenWithQueryParameters() {
        var pageParameters = new PageParameters.Builder()
                .url("https://www.example.com/products#page={pageNum}")
                .queryParam("q", "java")
                .queryParam("type", "Repositories")
                .build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products?q=java&type=Repositories#page=4", pageUrl);
    }

    @Test
    void testUrlPathSpecifierGiven() {
        var pageParameters = new PageParameters.Builder()
                .url("https://www.example.com/{pageNum}")
                .build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/4", pageUrl);
    }

    @Test
    void testUrlPathSpecifierGivenWithQueryParameters() {
        var pageParameters = new PageParameters.Builder()
                .url("https://www.example.com/{pageNum}")
                .queryParam("q", "java")
                .queryParam("type", "Repositories")
                .build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/4?q=java&type=Repositories", pageUrl);
    }

}