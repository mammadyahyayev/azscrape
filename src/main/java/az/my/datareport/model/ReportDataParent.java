package az.my.datareport.model;

import java.util.ArrayList;
import java.util.List;

public class ReportDataParent {
    private List<ReportDataElement> reportDataElements = new ArrayList<>();

    public ReportDataParent() {

    }

    public List<ReportDataElement> getReportDataElements() {
        return reportDataElements;
    }

    public void setReportDataElements(List<ReportDataElement> reportDataElements) {
        this.reportDataElements = new ArrayList<>(reportDataElements);
    }
}
