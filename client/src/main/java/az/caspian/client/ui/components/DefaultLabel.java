package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DefaultLabel extends JLabel implements SpaceAdjuster {
  public DefaultLabel(String lblName) {
    this.setText(lblName);
    this.setFont(Fonts.SANS_SERIF_BOLD_14);
    this.setForeground(Color.WHITE);
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
