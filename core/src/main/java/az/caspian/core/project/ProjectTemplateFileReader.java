package az.caspian.core.project;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ProjectTemplateFileReader {

  private final String content;

  public ProjectTemplateFileReader(String content) {
    this.content = content;
  }

  public char[] read() throws IOException {
    char[] chars = parseChars(content);

    String token = "";
    for (char character : chars) {

    }

    return null;
  }

  private void checkForToken(String token) {
    // check in the available tokens

    List<String> validTemplateTokens = ProjectTemplateTokenInitializer.TEMPLATE_TOKENS.stream()
      .map(ProjectTemplateToken::getName)
      .toList();

    if (!validTemplateTokens.contains(token)) {

    }
  }

  private char[] parseChars(String content) throws IOException {
    List<Character> chars = new ArrayList<>();
    StringReader stringReader = new StringReader(content);

    int read;
    while ((read = stringReader.read()) != -1) {
      char currentChar = (char) read;
      if (currentChar == ' ' || currentChar == '\t' || currentChar == '\n' || currentChar == '\r') {
        continue;
      }
      chars.add(currentChar);
    }

    return chars.stream()
      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
      .toString()
      .toCharArray();
  }
}
