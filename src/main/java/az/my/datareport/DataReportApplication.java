package az.my.datareport;

import az.my.datareport.ast.DataAST;
import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.exporter.Exporter;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import az.my.datareport.parser.ConfigFile;
import az.my.datareport.parser.ConfigFileManager;
import az.my.datareport.scanner.ConfigFileScanner;
import az.my.datareport.scanner.JsonConfigFileScanner;
import az.my.datareport.scrape.Scraper;
import az.my.datareport.scrape.WebScraper;

import java.io.File;

public final class DataReportApplication {

    private final Scraper scraper;
    private final Exporter exporter;

    public DataReportApplication() {
        scraper = new WebScraper();
        exporter = new ExcelExporter();
    }

    public void init(String configFilePath) {
        if (configFilePath == null || configFilePath.isEmpty()) {
            throw new NullPointerException("Please specify path of the config file!");
        }

        File file = new File(configFilePath);
        if (!file.isFile() || !file.exists()) {
            throw new IllegalArgumentException(String.format("File not found or path '%s' isn't refer to a file", configFilePath));
        }

        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST dataAST = scanner.readDataConfig(configFilePath);

        ConfigFileManager manager = new ConfigFileManager();
        ConfigFile configFile = manager.getConfigFile(configFilePath);

        ReportData reportData = scraper.scrape(dataAST);
        ReportFile reportFile = scanner.readFileConfig(configFile);
        exporter.export(reportFile, reportData);
    }

}
