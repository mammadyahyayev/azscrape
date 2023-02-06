package az.my.datareport.exporter;

import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;

import java.io.File;
import java.nio.file.Path;

public interface Exporter {

    /**
     * Resource Directory of the maven projects, e.g. src/main/resources
     */
    Path RESOURCE_DIR = Path.of("src", "main", "resources");

    /**
     * Exports scraped data into appropriate file
     *
     * @param reportFile exported file
     * @param reportData class contains scraped data
     */
    void export(ReportFile reportFile, ReportData reportData);

    /**
     * Constructs ReportFile that is going to being used to store
     * scraped data
     *
     * @param directory  given directory path, can be absolute or relative
     * @param reportFile exported file
     * @return constructed output file for export
     */
    File constructReportFile(String directory, ReportFile reportFile);

    /**
     * Constructs ReportFile in project resource folder, {@link #RESOURCE_DIR}
     *
     * @param reportFile exported file
     * @return constructed output file for export
     */
    File constructReportFile(ReportFile reportFile);
}
