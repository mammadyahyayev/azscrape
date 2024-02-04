package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultButton extends JButton implements SpaceAdjuster {
  public DefaultButton(String title) {
    this.setText(title);
    this.setFocusable(false);
    this.setFont(Fonts.SANS_SERIF_BOLD_14);
    this.setVerticalAlignment(JButton.CENTER);
    this.setHorizontalAlignment(JButton.CENTER);
    this.setSize(100, 200);
    this.setBackground(Colors.BASE_BTN_BG_COLOR);
    this.setForeground(Color.WHITE);
    this.setMargin(5);
  }

  public void setActionListener(ActionListener actionListener) {
    this.addActionListener(actionListener);
  }

  @Override
  public void setMargin(int margin) {
    this.setMargin(margin, margin, margin, margin);
  }

  @Override
  public void setMargin(int left, int top, int right, int bottom) {
    this.setBorder(new EmptyBorder(top, left, bottom, right));
  }
}
