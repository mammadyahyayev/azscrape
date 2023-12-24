package az.caspian.client.ui.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Utility class that is helpful for UI Management
 * for debugging in development
 */
public final class UiDebug {
  public static final Border RED_BORDER = BorderFactory.createLineBorder(Color.RED, 3);

  private UiDebug() {
    throw new IllegalStateException("Don't instantiate this class");
  }
}
