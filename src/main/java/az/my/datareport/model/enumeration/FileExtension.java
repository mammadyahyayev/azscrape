package az.my.datareport.model.enumeration;

public enum FileExtension {
    XLSX("xlsx"), TXT("txt"), JSON("json");

    private final String fileType;

    FileExtension(String fileType) {
        this.fileType = fileType;
    }

    public String fileType() {
        return fileType;
    }
}
