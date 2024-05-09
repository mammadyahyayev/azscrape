package az.caspian.core.tree.node;

public enum SelectorType {
  CLASS {
    @Override
    String convert(String pattern) {
      // divide each element and check whether each part has . sign or not.
      // if there is not any dot sign, then add it.
      return "";
    }
  },
  ID {
    @Override
    String convert(String pattern) {
      // divide each element and check whether each part has # sign or not.
      // if there is not a hash sign, then add it.
      return "";
    }
  },
  TAG {
    @Override
    String convert(String pattern) {
      // check whether it is a valid tag element or not
      return "";
    }
  },
  CUSTOM {
    @Override
    String convert(String pattern) {
      return pattern;
    }
  },
  XPATH {
    @Override
    String convert(String pattern) {
      return "";
    }
  };

  abstract String convert(String pattern);
}
