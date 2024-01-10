package az.caspian.client.ui.components;

import javax.swing.*;
import java.awt.*;

public class DefaultTextField extends JTextField {
  public DefaultTextField(String tooltip) {
    this.setToolTipText(tooltip);
    this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
  }
}
