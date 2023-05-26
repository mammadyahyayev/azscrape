package az.caspian.export;

import az.caspian.core.model.ReportFile;
import az.caspian.core.tree.ReportDataTable;

import java.io.File;

/**
 * Exports report files
 */
public interface Exporter {

    /**
     * Exports scraped data into appropriate file
     *
     * @param reportFile exported file
     * @param reportData class contains scraped data
     */
    boolean export(ReportFile reportFile, ReportDataTable reportData);

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
     * Constructs ReportFile in project resource folder
     *
     * @param reportFile exported file
     * @return constructed output file for export
     */
    File constructReportFile(ReportFile reportFile);
}