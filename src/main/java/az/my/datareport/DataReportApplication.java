package az.my.datareport;

import az.my.datareport.ast.DataAST;
import az.my.datareport.parser.ConfigFile;
import az.my.datareport.parser.ConfigFileValidity;
import az.my.datareport.parser.UnsupportedFileFormatException;
import az.my.datareport.scanner.ConfigFileScanner;
import az.my.datareport.scanner.JsonConfigFileScanner;
import az.my.datareport.scrape.Scraper;
import az.my.datareport.scrape.WebScraper;

public final class DataReportApplication {

    private DataAST dataAST;
    private final Scraper scraper;

    public DataReportApplication() {
        scraper = new WebScraper();
    }

    public void init(String configFilePath) {
        if (configFilePath == null || configFilePath.isEmpty()) {
            throw new NullPointerException("Please specify path of the config file!");
        }

        ConfigFile configFile = ConfigFileValidity.validateAndGet(configFilePath);

        switch (configFile.getFileExtension()) {
            case "json":
                ConfigFileScanner scanner = new JsonConfigFileScanner();
                dataAST = scanner.read(configFilePath);
            default:
                assert false;
                throw new UnsupportedFileFormatException(configFile.getFileExtension() + " is unsupported");
        }
    }

    private void tryToScrape() {
        scraper.scrape(dataAST);
    }

}
