package az.caspian.scrape;

/**
 * Throws, when user have problem to connect to {@code WebBrowser}
 *
 * @see WebBrowser
 */
public class WebBrowserException extends RuntimeException {
    public WebBrowserException(String message, Throwable cause) {
        super(message, cause);
    }
}
