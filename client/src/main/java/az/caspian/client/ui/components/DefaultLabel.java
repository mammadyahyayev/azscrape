package az.caspian.client.ui.components;

import javax.swing.*;
import java.awt.*;

public class DefaultLabel extends JLabel {
  public DefaultLabel(String lblName) {
    this.setText(lblName);
    this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    this.setForeground(Color.WHITE);
  }
}
