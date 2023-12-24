package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Colors;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
  public HeaderPanel() {
    this.setSize(this.getWidth(), 50);
    this.setBackground(Colors.BASE_BG_COLOR);

    JLabel headerLabel = new JLabel("AZScrape");
    headerLabel.setHorizontalTextPosition(JLabel.CENTER);
    headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    headerLabel.setForeground(Color.WHITE);

    this.add(headerLabel);
  }
}
