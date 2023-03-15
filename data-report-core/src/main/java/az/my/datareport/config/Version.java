package az.my.datareport.config;

public class Version {
    private final String major;
    private final String minor;
    private final String patch;

    public Version(String major, String minor, String patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public String getVersion() {
        return major + "." + minor + "." + patch;
    }
}
