package az.caspian.core.config;

/** Version of AZScrape. */
public record AzScrapeVersion(String major, String minor, String patch) {
  public String getVersion() {
    return major + "." + minor + "." + patch;
  }
}
