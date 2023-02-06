package az.my.datareport.model;

public class Report {
    private final String title;
    private final String description;
    private ReportData reportData;


    public Report(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ReportData getReportData() {
        return reportData;
    }
}
