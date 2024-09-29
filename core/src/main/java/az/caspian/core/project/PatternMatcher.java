package az.caspian.core.project;

public sealed interface PatternMatcher permits BlockPatternMatcher {
  boolean matches(String pattern);
}
