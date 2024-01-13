package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.UiConstants;

import javax.swing.*;

/**
 * The Dialog box is the decorator class for JOptionPane.
 * It is used to simplify long line codes with easy to understand methods.
 */
public final class MessageBox {

  private MessageBox() {
  }

  public static void error(String message, JFrame frame) {
    JOptionPane.showMessageDialog(frame, message, UiConstants.MAIN_FRAME_TITLE, JOptionPane.ERROR_MESSAGE);
  }

  public static void warning(String message, JFrame frame) {
    JOptionPane.showMessageDialog(frame, message, UiConstants.MAIN_FRAME_TITLE, JOptionPane.WARNING_MESSAGE);
  }

}
