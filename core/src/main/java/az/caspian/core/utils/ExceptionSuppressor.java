package az.caspian.core.utils;

import java.util.function.Consumer;

@FunctionalInterface
public interface ExceptionSuppressor<T> {
  void apply(T t) throws Exception;

  static <T> Consumer<T> wrap(ExceptionSuppressor<T> handler) {
    return t -> {
      try {
        handler.apply(t);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }
}
