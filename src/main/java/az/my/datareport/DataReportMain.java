package az.my.datareport;

import az.my.datareport.parser.ConfigReaderFactory;

public class DataReportMain {
    public static void main(String[] args) {
        ConfigReaderFactory.getReader(args[0]).read();
    }
}
