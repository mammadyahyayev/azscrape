package az.my.datareport.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageParametersTest {

    @Test
    void testWithoutPageSpecifier() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/products?page");
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class, pageParameters::build
        );

        assertEquals(
                "Page specifier isn't configured correctly!, begin=-1, end=-1",
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testWithoutEndingWrapperPageSpecifier() {
        var pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/products?page={");
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class, pageParameters::build
        );

        assertEquals(
                "Page specifier isn't configured correctly!, begin=38, end=-1",
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testWithoutBeginningWrapperPageSpecifier() {
        var pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/products?page=}");
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class, pageParameters::build
        );

        assertEquals(
                "Page specifier isn't configured correctly!, begin=-1, end=38",
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testWithoutPageSpecifierKey() {
        var pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/products?page={}");
        var illegalArgumentException = assertThrows(
                IllegalArgumentException.class, pageParameters::build
        );

        assertEquals(
                "Page specifier is in wrong place!, begin=38, pageKey=-1, end=39",
                illegalArgumentException.getMessage()
        );
    }

    @Test
    void testQueryParamSpecifierGiven() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/products?page={pageNum}");

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products?page=4", pageUrl);
    }

    @Test
    void testQueryParamSpecifierGivenWithQueryParameters() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.addQueryParameter(new QueryParameter("q", "java"));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories"));
        pageParameters.setPageUrl("https://www.example.com/products?page={pageNum}");
        pageParameters.build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products?page=4&q=java&type=Repositories", pageUrl);
    }

    @Test
    void testHashFragmentSpecifierGiven() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/products#page={pageNum}");
        pageParameters.build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products#page=4", pageUrl);
    }

    @Test
    void testHashFragmentSpecifierGivenWithQueryParameters() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.addQueryParameter(new QueryParameter("q", "java"));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories"));
        pageParameters.setPageUrl("https://www.example.com/products#page={pageNum}");
        pageParameters.build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/products?q=java&type=Repositories#page=4", pageUrl);
    }

    @Test
    void testUrlPathSpecifierGiven() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/{pageNum}");
        pageParameters.build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/4", pageUrl);
    }

    @Test
    void testUrlPathSpecifierGivenWithQueryParameters() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.addQueryParameter(new QueryParameter("q", "java"));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories"));
        pageParameters.setPageUrl("https://www.example.com/{pageNum}");
        pageParameters.build();

        String pageUrl = pageParameters.getPageUrl(4);

        assertEquals("https://www.example.com/4?q=java&type=Repositories", pageUrl);
    }

}