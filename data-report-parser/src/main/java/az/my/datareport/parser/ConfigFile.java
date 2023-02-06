package az.my.datareport.parser;

public class ConfigFile {
    private final String filename;
    private final String filepath;
    private final String fileExtension;

    public ConfigFile(String filename, String filepath, String fileExtension) {
        this.filename = filename;
        this.filepath = filepath;
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
}
