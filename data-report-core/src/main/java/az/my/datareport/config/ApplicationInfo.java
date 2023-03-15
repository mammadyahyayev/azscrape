package az.my.datareport.config;

public class ApplicationInfo {
    private final Author author;
    private final Version version;

    public ApplicationInfo(Author author, Version version) {
        this.author = author;
        this.version = version;
    }
}
