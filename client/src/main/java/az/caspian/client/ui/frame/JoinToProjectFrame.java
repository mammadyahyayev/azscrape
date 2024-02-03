package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.DefaultButton;
import az.caspian.client.ui.components.DefaultFrame;
import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;
import az.caspian.core.remote.ClientConnection;
import az.caspian.core.remote.Session;
import az.caspian.core.service.ClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JoinToProjectFrame extends DefaultFrame {
  private JTextField serverIpAddress;

  private final ClientService clientService;

  public JoinToProjectFrame(ClientService clientService) {
    super();

    this.clientService = clientService;

    this.setSize(400, 400);

    loadUi();

    this.setVisible(true);
  }

  private void loadUi() {
    var headerPanel = new HeaderPanel();
    JPanel contentPanel = createContentPanel();
    var footerPanel = new FooterPanel();

    this.add(headerPanel, BorderLayout.NORTH);
    this.add(contentPanel, BorderLayout.CENTER);
    this.add(footerPanel, BorderLayout.SOUTH);
  }

  private JPanel createContentPanel() {
    var contentPanel = new JPanel();
    contentPanel.setSize(300, 400);
    contentPanel.setLayout(new GridBagLayout());
    contentPanel.setBackground(Colors.BASE_BG_COLOR);

    serverIpAddress = new JTextField();
    serverIpAddress.setToolTipText("Enter Server IP Address");
    serverIpAddress.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    serverIpAddress.setSize(200, 40);

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(0, 0, 10, 0);
    contentPanel.add(serverIpAddress, gridConstraints);

    var createNewProjectBtn = new DefaultButton("Create new Project");
    createNewProjectBtn.setActionListener(this::openCreateProjectFrameAction);

    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.gridwidth = 1;
    gridConstraints.insets = new Insets(0, 0, 0, 10);
    contentPanel.add(createNewProjectBtn, gridConstraints);

    var joinToProjectBtn = new DefaultButton("Join to Project");
    joinToProjectBtn.setActionListener(this::joinToProjectAction);

    gridConstraints.gridx = 1;
    gridConstraints.gridy = 1;
    gridConstraints.gridwidth = 1;
    gridConstraints.insets = new Insets(0, 10, 0, 0);
    contentPanel.add(joinToProjectBtn, gridConstraints);

    return contentPanel;
  }

  private void openCreateProjectFrameAction(ActionEvent event) {
    this.dispose();
    new CreateProjectFrame(clientService);
  }

  private void joinToProjectAction(ActionEvent event) {
    String ipAddress = serverIpAddress.getText();
    Session.setServerIpAddress(ipAddress);
    ClientConnection.joinToProject(Session.getCurrentClient());
  }
}
