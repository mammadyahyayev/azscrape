package az.my.datareport.model;

import java.util.ArrayList;
import java.util.List;

//TODO: reportDataElements is sensible variable, add helper methods
// and remove setters
public class ReportData {
    private List<ReportDataParent> reportParentElements = new ArrayList<>();

    public ReportData() {

    }

    public List<ReportDataParent> getReportParentElements() {
        return reportParentElements;
    }

    public void setReportParentElements(List<ReportDataParent> reportParentElements) {
        this.reportParentElements = new ArrayList<>(reportParentElements);
    }
}
