package az.caspian.client.ui.frame;

import az.caspian.client.Session;
import az.caspian.client.service.ClientService;
import az.caspian.client.ui.components.DefaultButton;
import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.UiConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JoinToProjectFrame extends JFrame {
  private final ClientService clientService;

  private JTextField projectIdTxt;

  public JoinToProjectFrame(ClientService clientService) {
    this.clientService = clientService;

    initUi();
  }

  private void initUi() {
    this.setTitle(UiConstants.MAIN_FRAME_TITLE);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.getContentPane().setBackground(Colors.BASE_BG_COLOR);
    this.setSize(400, 400);
    this.setLocation(700, 380);

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

    projectIdTxt = new JTextField();
    projectIdTxt.setToolTipText("Enter Project ID");
    projectIdTxt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    projectIdTxt.setSize(200, 40);

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(0, 0, 10, 0);
    contentPanel.add(projectIdTxt, gridConstraints);

    var createNewProjectBtn = new DefaultButton("Create new Project");
    createNewProjectBtn.setActionListener(this::openCreateProjectFrameAction);

    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.gridwidth = 1;
    gridConstraints.insets = new Insets(0, 0, 0, 10);
    contentPanel.add(createNewProjectBtn, gridConstraints);

    var joinToProjectBtn = new DefaultButton("Join To Project");
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
    new CreateProjectFrame();
  }

  private void joinToProjectAction(ActionEvent event) {
    String projectId = projectIdTxt.getText();
    clientService.joinToProject(projectId, Session.getCurrentClient());
  }
}
