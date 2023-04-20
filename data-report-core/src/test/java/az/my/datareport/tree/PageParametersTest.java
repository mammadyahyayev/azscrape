package az.my.datareport.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageParametersTest {

    @Test
    void testQueryParameters() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/search");

        pageParameters.addQueryParameter(new QueryParameter("q", "java", false));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories", false));
        pageParameters.addQueryParameter(new QueryParameter("p", "0", true));

        int pageNumber = 4;
        String pageUrl = pageParameters.buildPageUrl(pageNumber);

        assertEquals("https://www.example.com/search?q=java&type=Repositories&p=" + pageNumber, pageUrl);
    }

    @Test
    void testBuildPageUrl_whenPageQueryParameterNotExist() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com/search");

        pageParameters.addQueryParameter(new QueryParameter("q", "java", false));
        pageParameters.addQueryParameter(new QueryParameter("type", "Repositories", false));
        pageParameters.addQueryParameter(new QueryParameter("p", "0", false));

        String pageUrl = pageParameters.buildPageUrl(4);

        assertEquals("https://www.example.com/search?q=java&type=Repositories&p=0", pageUrl);
    }

    @Test
    void testBuildPageUrl_whenQueryParametersNotExist() {
        PageParameters pageParameters = new PageParameters();
        pageParameters.setPageUrl("https://www.example.com");

        String pageUrl = pageParameters.buildPageUrl(4);

        assertEquals("https://www.example.com/4", pageUrl);
    }

}