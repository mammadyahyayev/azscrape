package az.my.datareport.exporter;

import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface Exporter {
    void export(ReportFile reportFile, ReportData reportData);
    File constructReportFile(String directory, ReportFile reportFile);
    File constructReportFile(ReportFile reportFile);
}
