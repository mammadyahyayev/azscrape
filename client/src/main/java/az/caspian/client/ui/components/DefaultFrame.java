package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.UiConstants;

import javax.swing.*;
import java.awt.*;

public class DefaultFrame extends JFrame {
  protected DefaultFrame() {
    this.setTitle(UiConstants.MAIN_FRAME_TITLE);
    this.setResizable(false);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.getContentPane().setBackground(Colors.BASE_BG_COLOR);
    this.setSize(500, 400);
    this.setLocation(700, 380);
  }
}
