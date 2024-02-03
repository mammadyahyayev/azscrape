package az.caspian.client.ui.frame;

import az.caspian.core.remote.Session;
import az.caspian.core.service.ClientService;
import az.caspian.client.ui.components.*;
import az.caspian.client.ui.constants.Colors;
import az.caspian.core.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateProjectFrame extends DefaultFrame {
  private final ClientService clientService;

  private DefaultTextField projectNameTxt;

  public CreateProjectFrame(ClientService clientService) {
    super();
    this.clientService = clientService;

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
    contentPanel.setSize(450, 400);
    contentPanel.setLayout(new GridBagLayout());
    contentPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectNameLbl = new DefaultLabel("Project Name");

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 0);
    gridConstraints.weightx = 0.1;
    contentPanel.add(projectNameLbl, gridConstraints);

    projectNameTxt = new DefaultTextField("Enter Project Name");

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    gridConstraints.weightx = 0.9;
    contentPanel.add(projectNameTxt, gridConstraints);

    var createNewProjectBtn = new DefaultButton("Create new Project");
    createNewProjectBtn.setActionListener(this::createNewProjectAction);

    gridConstraints.gridx = 0;
    gridConstraints.gridy = 3;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(0, 10, 0, 10);
    contentPanel.add(createNewProjectBtn, gridConstraints);

    return contentPanel;
  }

  private void createNewProjectAction(ActionEvent event) {
    String projectName = projectNameTxt.getText();
    if (StringUtils.isNullOrEmpty(projectName)) {
      MessageBox.error("Project Name cannot be empty", this);
    }

    boolean isCreated = clientService.createProject(projectName, Session.getCurrentClient());
    if (isCreated) {
      MessageBox.info("Project created successfully.", this);
    } else {
      MessageBox.error("Problem happened while creation of project!", this);
    }
  }
}
