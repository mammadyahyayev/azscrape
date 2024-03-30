package az.caspian.scrape.templates.multiurl;

import az.caspian.core.utils.Asserts;
import az.caspian.scrape.templates.TemplateException;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//TODO: Consumer can give urls as file which contains urls separated by newline (\n) character
// or it can give urls as List, e.g List.of("http://www.example.com", "http://www.example2.com")
// there will be cases to remove duplicate urls or ignore already visited urls, in this case
// we can store their hash values inside map to check whether url is visited or not.

//TODO: It is possible that urls can be given in file, however url is wrong or not a url, then
// in this case, throwing exception won't be good, instead, we can provide additional API to user
// to select whether he wants to fail fast or continue if url is wrong, by default program will
// continue, however user can change this

//TODO: If urlSourceFilePath is given, scraper will first check urls is given or not, if it is given,
// then scraper will first scrape those urls and then continue with url source file path.
public class MultiUrlTemplateParameters {
  private final Set<String> urls;
  private final long delayBetweenUrls;
  private final boolean failFast;
  private final Path urlSourceFilePath;

  private MultiUrlTemplateParameters(Builder builder) {
    this.urls = builder.urls;
    this.delayBetweenUrls = builder.delayBetweenUrls;
    this.failFast = builder.failFast;
    this.urlSourceFilePath = builder.urlSourceFilePath;
  }

  public static class Builder {
    private final Set<String> urls = new HashSet<>();
    private long delayBetweenUrls;
    private boolean failFast;
    private Path urlSourceFilePath;

    public Builder urls(Set<String> urls) {
      this.urls.addAll(urls);
      return this;
    }

    public Builder urlSource(Path urlSourceFilePath) {
      Asserts.required(urlSourceFilePath, "urlSourceFilePath can't be null!");

      boolean isExist = Files.exists(urlSourceFilePath);
      if (!isExist || Files.isDirectory(urlSourceFilePath)) {
        throw new TemplateException("Path isn't exist or it is a directory",
          new FileNotFoundException("URL sources file %s not found".formatted(urlSourceFilePath)));
      }

      this.urlSourceFilePath = urlSourceFilePath;
      return this;
    }

    public Builder delayBetweenUrls(long delay, TimeUnit timeUnit) {
      this.delayBetweenUrls = timeUnit.toMillis(delay);
      return this;
    }

    public Builder failFast(boolean failFast) {
      this.failFast = failFast;
      return this;
    }

    public MultiUrlTemplateParameters build() {
      return new MultiUrlTemplateParameters(this);
    }
  }

  public Set<String> getUrls() {
    return urls;
  }

  public long getDelayBetweenUrls() {
    return delayBetweenUrls;
  }

  public boolean isFailFast() {
    return failFast;
  }

  public Path getUrlSourceFilePath() {
    return urlSourceFilePath;
  }
}
