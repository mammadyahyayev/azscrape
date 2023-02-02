package az.my.datareport.config;

public abstract class ConfigFile {
    private final String filename;
    private final String filepath;
    private final String absolutePath;
    private final String fileExtension;

    public ConfigFile(String filename, String filepath, String absolutePath, String fileExtension) {
        this.filename = filename;
        this.filepath = filepath;
        this.absolutePath = absolutePath;
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

    public String getAbsolutePath() {
        return absolutePath;
    }
}