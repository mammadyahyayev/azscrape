package az.caspian.core.project;

import java.util.HashSet;
import java.util.Set;

public final class ProjectTemplateTokenInitializer {

  protected static final Set<ProjectTemplateToken> TEMPLATE_TOKENS = new HashSet<>();

  static {
    // words
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("project", ProjectTemplateToken.TokenType.WORD));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("properties", ProjectTemplateToken.TokenType.WORD));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("template", ProjectTemplateToken.TokenType.WORD));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("parameters", ProjectTemplateToken.TokenType.WORD));

    // symbols
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("{", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("}", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("<", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken(">", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken(":", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("'", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken(",", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("[", ProjectTemplateToken.TokenType.SYMBOL));
    TEMPLATE_TOKENS.add(new ProjectTemplateToken("]", ProjectTemplateToken.TokenType.SYMBOL));
  }

}
