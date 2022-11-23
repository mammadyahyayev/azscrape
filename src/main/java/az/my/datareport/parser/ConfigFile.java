package az.my.datareport.parser;

public class ConfigFile {
    private final String filename;
    private final String filepath;
    private final String fileType;
    private final String fileExtension;

    public ConfigFile(String filename, String filepath, String fileType, String fileExtension) {
        this.filename = filename;
        this.filepath = filepath;
        this.fileType = fileType;
        this.fileExtension = fileExtension;
    }

    public String getFilename() {
        return filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFileType() {
        return fileType;
    }
}
