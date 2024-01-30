package az.caspian.client.ui.components;

import az.caspian.client.ui.constants.UiConstants;

import javax.swing.*;

import static javax.swing.JOptionPane.*;

/**
 * The Dialog box is the decorator class for JOptionPane.
 * It is used to simplify long line codes with easy to understand methods.
 */
public final class MessageBox {

  private MessageBox() {
  }

  public static void error(String message, JFrame frame) {
    showMessageBox(message, frame, ERROR_MESSAGE);
  }

  public static void info(String message, JFrame frame) {
    showMessageBox(message, frame, INFORMATION_MESSAGE);
  }

  public static void warning(String message, JFrame frame) {
    showMessageBox(message, frame, WARNING_MESSAGE);
  }

  private static void showMessageBox(String message, JFrame frame, int messageType) {
    JOptionPane.showMessageDialog(frame, message, UiConstants.MAIN_FRAME_TITLE, messageType);
  }

}
