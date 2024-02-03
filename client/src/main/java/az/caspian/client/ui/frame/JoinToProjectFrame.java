package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.*;
import az.caspian.client.ui.constants.Colors;
import az.caspian.core.remote.ClientConnection;
import az.caspian.core.remote.Session;
import az.caspian.core.service.ClientService;
import az.caspian.core.service.ProjectService;
import az.caspian.core.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JoinToProjectFrame extends DefaultFrame {
  private JTextField serverIpAddress;

  private final ProjectService projectService;
  private final ClientService clientService;

  public JoinToProjectFrame(ProjectService projectService, ClientService clientService) {
    super();
    this.projectService = projectService;

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

    var shareProjectBtn = new DefaultButton("Share Project");
    shareProjectBtn.setActionListener(this::openShareProjectFrameAction);

    gridConstraints.gridx = 0;
    gridConstraints.gridy = 2;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(10, 0, 0, 0);
    contentPanel.add(shareProjectBtn, gridConstraints);

    return contentPanel;
  }

  private void openCreateProjectFrameAction(ActionEvent event) {
    this.dispose();
    new CreateProjectFrame(clientService);
  }

  private void openShareProjectFrameAction(ActionEvent event) {
    this.dispose();
    new ShareProjectFrame(projectService);
  }

  private void joinToProjectAction(ActionEvent event) {
    String ipAddress = serverIpAddress.getText();
    if (StringUtils.isNullOrEmpty(ipAddress)) {
      MessageBox.error("IP address must not be null!", this);
      return;
    }

    Session.setServerIpAddress(ipAddress);
    ClientConnection.joinToProject(Session.getCurrentClient());
  }
}
