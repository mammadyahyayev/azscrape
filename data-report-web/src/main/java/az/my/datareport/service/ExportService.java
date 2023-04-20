package az.my.datareport.service;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.model.ReportFile;
import az.my.datareport.tree.ReportDataTable;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    private final ExcelExporter exporter;

    public ExportService(ExcelExporter exporter) {
        this.exporter = exporter;
    }

    public boolean export(ReportFile reportFile, ReportDataTable reportData) {
        return exporter.export(reportFile, reportData);
    }

}
