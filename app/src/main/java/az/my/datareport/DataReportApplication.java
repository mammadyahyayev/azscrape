package az.my.datareport;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.exporter.Exporter;
import az.my.datareport.scrape.Scraper;

/**
 * Entry point of the application after parsing
 * command line arguments
 */
public final class DataReportApplication {

    private final Scraper scraper;
    private final Exporter exporter;

    public DataReportApplication() {
        scraper = null;
        exporter = new ExcelExporter();
    }

    /**
     * Initialize config file, scrapes and exports data
     *
     * @param arguments command line arguments
     */
    public void init(String[] arguments) {
        /*if (arguments == null || arguments.length == 0) {
            throw new ConfigFileException("Please specify path of the config file!");
        }

        String configFilePath = arguments[0];

        File file = new File(configFilePath);
        if (!file.isFile() || !file.exists()) {
            throw new IllegalArgumentException(String.format("File not found or path '%s' isn't refer to a file", configFilePath));
        }

        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST tree = scanner.readDataConfig(configFilePath);

        //TODO: Fix error
        *//*ConfigFileManager manager = new ConfigFileManager();
        ConfigFile configFile = manager.getConfigFile(configFilePath);*//*

        ReportData reportData = scraper2.scrape(tree);
        ReportFile reportFile = scanner.readFileConfig(null); // Change
        exporter.export(reportFile, reportData);*/
    }

}
