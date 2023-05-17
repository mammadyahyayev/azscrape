package az.caspian.scrape;

import az.caspian.core.utils.Asserts;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebBrowser {
    private static final Logger LOG = LogManager.getLogger(WebPage.class);

    private static final WebDriver driver;
    private boolean isOpen;
    private final boolean keepOpen;

    static {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setLogLevel(ChromeDriverLogLevel.OFF);
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
    }

    public WebBrowser() {
        this.keepOpen = false;
    }

    public WebBrowser(boolean keepOpen) {
        this.keepOpen = keepOpen;
    }

    /**
     * Opens Web Browser
     */
    public void open() {
        try {
            driver.manage().window().maximize();
            isOpen = true;
        } catch (Exception e) {
            close();
            throw new InternetConnectionException("Failed to connect to webpage, check your internet connection!", e);
        }
    }


    /**
     * Closes Web Browser
     */
    public void close() {
        if (isOpen) {
            driver.quit();
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

    public WebPage goTo(final String url, int delayInMs) {
        // TODO: Check url is valid
        Asserts.required(url, "url cannot be null or empty");
        if (delayInMs < 0) {
            delayInMs = 0;
        }

        try {
            Thread.sleep(delayInMs);
            driver.get(url);
        } catch (Exception e) {
            LOG.error("Failed to connect to web page ", e);
            close();
            throw new InternetConnectionException("Failed to connect to webpage, check your internet connection!", e);
        }

        return new WebPage(url, driver);
    }
}