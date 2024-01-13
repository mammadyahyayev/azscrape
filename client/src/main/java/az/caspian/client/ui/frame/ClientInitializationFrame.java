package az.caspian.client.ui.frame;

import az.caspian.client.service.ClientService;
import az.caspian.client.ui.components.*;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.UiConstants;
import az.caspian.core.messaging.ClientInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClientInitializationFrame extends JFrame {
  private final ClientService clientService;

  private JTextField lastNameTxt;
  private JTextField firstNameTxt;
  private JTextField emailTxt;

  public ClientInitializationFrame(ClientService clientService) {
    this.clientService = clientService;

    initFrame();
  }

  private void initFrame() {
    this.setTitle(UiConstants.MAIN_FRAME_TITLE);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.getContentPane().setBackground(Colors.BASE_BG_COLOR);
    this.setSize(500, 400);
    this.setLocation(700, 380);

    addContent();

    this.setVisible(true);
  }

  private void addContent() {
    var headerPanel = new HeaderPanel();
    JPanel contentPanel = createContentPanel();
    var footerPanel = new FooterPanel();

    this.add(headerPanel, BorderLayout.NORTH);
    this.add(contentPanel, BorderLayout.CENTER);
    this.add(footerPanel, BorderLayout.SOUTH);
  }

  private JPanel createContentPanel() {
    var contentPanel = new JPanel();
    contentPanel.setSize(450, 400);
    contentPanel.setLayout(new GridBagLayout());
    contentPanel.setBackground(Colors.BASE_BG_COLOR);

    //region LastName
    var lastNameLbl = new DefaultLabel("Lastname");

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 0);
    gridConstraints.weightx = 0.1;
    contentPanel.add(lastNameLbl, gridConstraints);

    lastNameTxt = new DefaultTextField("Enter your lastname");

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    gridConstraints.weightx = 0.9;
    contentPanel.add(lastNameTxt, gridConstraints);
    //endregion

    //region FirstName
    var firstNameLbl = new DefaultLabel("Firstname");

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 0);
    gridConstraints.weightx = 0.1;
    contentPanel.add(firstNameLbl, gridConstraints);

    firstNameTxt = new DefaultTextField("Enter your firstname");

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 1;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    gridConstraints.weightx = 0.9;
    contentPanel.add(firstNameTxt, gridConstraints);
    //endregion

    //region Email
    var emailLbl = new DefaultLabel("Email");

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 2;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 0);
    gridConstraints.weightx = 0.1;
    contentPanel.add(emailLbl, gridConstraints);

    emailTxt = new DefaultTextField("Enter your email");

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 2;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    gridConstraints.weightx = 0.9;
    contentPanel.add(emailTxt, gridConstraints);
    //endregion

    var saveClientInfoBtn = new DefaultButton("Save");
    saveClientInfoBtn.setActionListener(this::saveClientInfoAction);

    gridConstraints.gridx = 0;
    gridConstraints.gridy = 3;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(0, 10, 0, 10);
    contentPanel.add(saveClientInfoBtn, gridConstraints);

    return contentPanel;
  }

  private void saveClientInfoAction(ActionEvent event) {
    var clientInfo = new ClientInfo();
    clientInfo.setLastName(lastNameTxt.getText());
    clientInfo.setFirstName(firstNameTxt.getText());
    clientInfo.setEmail(emailTxt.getText());

    boolean isSaved = clientService.saveClientInfo(clientInfo);
    if (isSaved) {
      this.dispose();
      new JoinToProjectFrame(clientService);
    } else {
      MessageBox.error("Problem happened when saving your information!", this);
    }
  }

}
