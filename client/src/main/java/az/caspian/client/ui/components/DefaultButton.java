package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultButton extends JButton {
  public DefaultButton(String title) {
    this.setText(title);
    this.setFocusable(false);
    this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    this.setVerticalAlignment(JButton.CENTER);
    this.setHorizontalAlignment(JButton.CENTER);
    this.setSize(100, 200);
    this.setBackground(Colors.BASE_BTN_BG_COLOR);
    this.setForeground(Color.WHITE);
    this.setBorder(new EmptyBorder(5, 5, 5, 5));
  }

  public void setActionListener(ActionListener actionListener) {
    this.addActionListener(actionListener);
  }
}
