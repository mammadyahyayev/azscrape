package az.caspian.core.template;

import az.caspian.core.messaging.Client;

/**
 * The enum is used to create tasks by split each {@link ScrapeTemplate} based on given
 * {@link SplitStrategy}.
 */
public enum SplitStrategy {
  /**
   * {@link ScrapeTemplate} will be split equally for each {@link Client}. This is the default {@link SplitStrategy}.
   */
  EQUAL,

  /**
   * {@link ScrapeTemplate} will be split based on {@link Client}'s system (CPU, RAM, SSD and so on.)
   */
  SYSTEM_POWER;
}
