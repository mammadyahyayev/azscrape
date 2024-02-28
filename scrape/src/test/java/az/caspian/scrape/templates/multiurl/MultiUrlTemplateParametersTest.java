package az.caspian.scrape.templates.multiurl;

import az.caspian.scrape.templates.TemplateException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultiUrlTemplateParametersTest {

  @Test
  void throwException_whenGivenUrlSourceFileNotExist() {
    TemplateException templateException = assertThrows(TemplateException.class, () ->
      new MultiUrlTemplateParameters.Builder().urlSource(Path.of("not-exist-path")));

    assertThat(templateException.getCause()).isInstanceOf(FileNotFoundException.class);
  }

}