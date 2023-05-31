package az.caspian.export;

import az.caspian.core.model.DataFile;
import az.caspian.core.tree.ReportDataTable;

import java.io.File;

/**
 * Exports report files
 */
public interface Exporter {

    /**
     * Exports scraped data into appropriate file
     *
     * @param dataFile exported file
     * @param reportData class contains scraped data
     */
    boolean export(DataFile dataFile, ReportDataTable reportData);

    /**
     * Constructs ReportFile that is going to being used to store
     * scraped data
     *
     * @param directory  given directory path, can be absolute or relative
     * @param dataFile exported file
     * @return constructed output file for export
     */
    File constructReportFile(String directory, DataFile dataFile);

    /**
     * Constructs ReportFile in project resource folder
     *
     * @param dataFile exported file
     * @return constructed output file for export
     */
    File constructReportFile(DataFile dataFile);
}
