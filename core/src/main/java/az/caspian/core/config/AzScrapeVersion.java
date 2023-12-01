package az.caspian.core.config;

/** Version of AZScrape. */
public class AzScrapeVersion {
  private final String major;
  private final String minor;
  private final String patch;

  public AzScrapeVersion(String major, String minor, String patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  public String getVersion() {
    return major + "." + minor + "." + patch;
  }
}
