package az.caspian.client.ui;

import az.caspian.client.ui.components.DefaultButton;
import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateProjectFrame extends JFrame {
  public CreateProjectFrame() {
    this.setTitle("AZScrape Client");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.getContentPane().setBackground(Colors.BASE_BG_COLOR);
    this.setSize(500, 400);
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
    contentPanel.setSize(450, 400);
    contentPanel.setLayout(new GridBagLayout());
    contentPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectNameLbl = new JLabel("Project Name");
    projectNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    projectNameLbl.setForeground(Color.WHITE);

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 0);
    gridConstraints.weightx = 0.1;
    contentPanel.add(projectNameLbl, gridConstraints);

    var projectNameTxt = new JTextField();
    projectNameTxt.setToolTipText("Enter Project Name");
    projectNameTxt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    gridConstraints.weightx = 0.9;
    contentPanel.add(projectNameTxt, gridConstraints);

    var projectOwnerLbl = new JLabel("Project Owner");
    projectOwnerLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    projectOwnerLbl.setForeground(Color.WHITE);

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 0);
    gridConstraints.weightx = 0.1;
    contentPanel.add(projectOwnerLbl, gridConstraints);

    var projectOwnerTxt = new JTextField();
    projectOwnerTxt.setToolTipText("Enter Project Owner");
    projectOwnerTxt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 1;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    gridConstraints.weightx = 0.9;
    contentPanel.add(projectOwnerTxt, gridConstraints);

    var projectLocationLbl = new JLabel("Project Location");
    projectLocationLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    projectLocationLbl.setForeground(Color.WHITE);

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 2;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 10, 10, 10);
    contentPanel.add(projectLocationLbl, gridConstraints);

    var projectLocationTxt = new JTextField();
    projectLocationTxt.setToolTipText("Enter Project Abstract Location");
    projectLocationTxt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 1;
    gridConstraints.gridy = 2;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.insets = new Insets(0, 0, 10, 10);
    contentPanel.add(projectLocationTxt, gridConstraints);

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

  }

}
