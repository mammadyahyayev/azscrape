package az.my.datareport;

public class DataReportAppException extends RuntimeException {
    public DataReportAppException() {
        super();
    }

    public DataReportAppException(String message) {
        super(message);
    }

    public DataReportAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataReportAppException(Throwable cause) {
        super(cause);
    }
}
