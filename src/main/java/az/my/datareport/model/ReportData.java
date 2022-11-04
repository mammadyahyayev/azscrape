package az.my.datareport.model;

import java.util.ArrayList;
import java.util.List;

//TODO: reportDataElements is sensible variable, add helper methods
// and remove setters
public class ReportData {
    private String url;
    private List<ReportDataElement> reportDataElements = new ArrayList<>();

    public ReportData() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ReportDataElement> getReportDataElements() {
        return reportDataElements;
    }

    public void setReportDataElements(List<ReportDataElement> reportDataElements) {
        this.reportDataElements = new ArrayList<>(reportDataElements);
    }
}
