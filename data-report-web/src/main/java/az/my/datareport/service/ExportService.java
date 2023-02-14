package az.my.datareport.service;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private final ExcelExporter exporter;

    public ExportService(ExcelExporter exporter) {
        this.exporter = exporter;
    }

    public boolean export(ReportFile reportFile, ReportData reportData) {
        return exporter.export(reportFile, reportData);
    }

}
