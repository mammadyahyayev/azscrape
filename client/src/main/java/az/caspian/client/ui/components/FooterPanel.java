package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Colors;

import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {
  public FooterPanel() {
    this.setSize(this.getWidth(), 50);
    this.setBackground(Colors.BASE_BTN_BG_COLOR);

    JLabel footerLabel = new JLabel("Copyright - Mammad Yahyayev");
    footerLabel.setHorizontalTextPosition(JLabel.CENTER);
    footerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    footerLabel.setForeground(Color.WHITE);

    this.add(footerLabel);
  }
}
