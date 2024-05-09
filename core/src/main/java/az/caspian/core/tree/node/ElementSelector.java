package az.caspian.core.tree.node;

public record ElementSelector(
  String pattern,
  SelectorType selectorType
) {

  public String pattern() {
    return switch (selectorType) {
      case ID -> SelectorType.ID.convert(pattern);
      case CLASS -> SelectorType.CLASS.convert(pattern);
      case TAG -> SelectorType.TAG.convert(pattern);
      case CUSTOM -> SelectorType.CUSTOM.convert(pattern);
      case XPATH -> SelectorType.XPATH.convert(pattern);
    };
  }

}
