package az.caspian.core.project;

public class ProjectTemplateToken {
  private final String name;
  private final TokenType tokenType;
  private final PatternMatcher patternMatcher;

  public ProjectTemplateToken(String name, TokenType tokenType) {
    this.name = name;
    this.tokenType = tokenType;
    this.patternMatcher = null;
  }

  enum TokenType {
    WORD, SYMBOL
  }

  public String getName() {
    return name;
  }

  public TokenType getTokenType() {
    return tokenType;
  }

  public PatternMatcher getPatternMatcher() {
    return patternMatcher;
  }
}

