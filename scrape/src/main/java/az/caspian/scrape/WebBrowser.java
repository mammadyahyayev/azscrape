package az.caspian.scrape;

import az.caspian.core.utils.Asserts;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebBrowser implements AutoCloseable {
    private static final Logger LOG = LogManager.getLogger(WebPage.class);

    static final WebDriver DRIVER;
    private boolean isOpen;

    static {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setLogLevel(ChromeDriverLogLevel.OFF);
        chromeOptions.addArguments("--remote-allow-origins=*");
        DRIVER = new ChromeDriver(chromeOptions);
    }

    public WebBrowser() {

    }

    /**
     * Opens Web Browser
     */
    public void open() {
        try {
            DRIVER.manage().window().maximize();
            isOpen = true;
        } catch (Exception e) {
            close();
            throw new InternetConnectionException("Failed to connect to webpage, check your internet connection!", e);
        }
    }


    /**
     * Closes Web Browser
     */
    @Override
    public void close() {
        if (isOpen) {
            DRIVER.quit();
            isOpen = false;
        }
    }

    /**
     * Checks whether Web Browser is opened
     *
     * @return {@code true} if the Web Browser is open, otherwise {@code false}
     */
    public boolean isOpen() {
        return isOpen;
    }

    public WebPage goTo(final String url) {
        return goTo(url, 0);
    }

    /**
     * Connects to a web page with delay. It is useful
     * when connecting multiple Web page at the same session.
     *
     * @param delayInMillis delay in milliseconds
     */
    public WebPage goTo(final String url, int delayInMillis) {
        // TODO: Check url is valid
        Asserts.required(url, "url cannot be null or empty");
        if (delayInMillis < 0) {
            delayInMillis = 0;
        }

        try {
            Thread.sleep(delayInMillis);
            DRIVER.get(url);
        } catch (Exception e) {
            LOG.error("Failed to connect to web page ", e);
            close();
            throw new InternetConnectionException("Failed to connect to webpage, check your internet connection!", e);
        }

        return new WebPage(url);
    }
}