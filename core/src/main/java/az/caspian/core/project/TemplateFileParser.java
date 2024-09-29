package az.caspian.core.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TemplateFileParser {

  private static final Logger LOG = LogManager.getLogger(TemplateFileParser.class);

  private final Path templateFilePath;

  public TemplateFileParser(Path templateFilePath) {
    this.templateFilePath = templateFilePath;
  }

  public void parse() {
    // todo: consider, there can be empty lines in the file syntax, consider those facts as well.
    try {
      List<String> lines = Files.readAllLines(templateFilePath);
      String firstLine = lines.get(0);
      String lastLine = lines.get(lines.size() - 1);
      if (!firstLine.startsWith("project {") && !lastLine.endsWith("}")) {
        throw new TemplateSyntaxException("Template file syntax is incorrect. Template file must begin with 'project {' and end with '}'.");
      }
    } catch (IOException e) {
      LOG.error("Failed to read template file path {}", templateFilePath, e);
      // throw new RuntimeException("Failed to read template file path!", e); //todo: provide specific exception
    }
  }
}
