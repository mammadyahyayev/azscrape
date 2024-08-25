package az.caspian.core.performance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public final class ExecTimeProfiler {

  private static final Logger LOG = LogManager.getLogger(ExecTimeProfiler.class);

  private ExecTimeProfiler() {
    throw new IllegalStateException("Can't instantiate utility class");
  }

  static Duration profile(MeasurableOperation measurableOperation) {
    long startTime = System.currentTimeMillis();
    try {
      measurableOperation.execute();
    } finally {
      long elapsedTime = System.currentTimeMillis() - startTime;
      LOG.debug("Operation took: {} ms", elapsedTime);
    }

    return Duration.ofNanos(System.nanoTime() - startTime);
  }

}
