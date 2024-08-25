package az.caspian.core.performance;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExecTimeProfilerTest {

  @Test
  void shouldPrintExecution() {
    Duration execTimeDuration = ExecTimeProfiler.profile(() -> {
      try {
        Thread.sleep(4000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    assertTrue(3 < execTimeDuration.toSeconds());
  }

}