package az.caspian.core.project;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTemplateFileReaderTest {

  @Test
  void test() throws IOException {
    String content = """
      project {
            
      }
      """;

    char[] chars = new ProjectTemplateFileReader(content).read();
    System.out.println(Arrays.toString(chars));
  }


}