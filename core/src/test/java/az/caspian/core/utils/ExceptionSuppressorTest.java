package az.caspian.core.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.function.Consumer;

class ExceptionSuppressorTest {

  @Test
  void suppress_IOException() {
    Consumer<File> readFileContentSafely = ExceptionSuppressor.wrap(file -> {
      try (FileInputStream fileInputStream = new FileInputStream(file)) {
        int read = fileInputStream.read();
        System.out.println((char) read);
      }
    });

    readFileContentSafely.accept(new File("test.txt"));
  }

}