package az.my.datareport.parser;

import az.my.datareport.model.ReportData;

import java.util.List;

@Deprecated
public interface ConfigReader {
    List<ReportData> read();
}
