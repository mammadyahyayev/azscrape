package az.caspian.core.constant;

import az.caspian.core.utils.PropertiesFileSystem;

import java.util.Properties;

public final class AzScrapeVersion {
  private static final String VERSION;

  static {
    var inputStream = AzScrapeVersion.class.getClassLoader().getResourceAsStream("version.properties");
    Properties versionProperties = new PropertiesFileSystem().load(inputStream);
    VERSION = (String) versionProperties.get("azscrape.version");
  }

  public static String getVersion() {
    return VERSION;
  }

  private AzScrapeVersion() {
  }
}
