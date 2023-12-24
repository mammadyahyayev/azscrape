package az.caspian.client.ui;

import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JoinToProjectFrame extends JFrame {
  private JButton joinToProjectBtn;
  private JButton createNewProjectBtn;

  public JoinToProjectFrame() {
    this.setTitle("AZScrape Client");
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

    JTextField projectIdTxt = new JTextField();
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

    createNewProjectBtn = new JButton("Create new Project");
    createNewProjectBtn.setFocusable(false);
    createNewProjectBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    createNewProjectBtn.setVerticalAlignment(JButton.CENTER);
    createNewProjectBtn.setHorizontalAlignment(JButton.CENTER);
    createNewProjectBtn.setSize(100, 200);
    createNewProjectBtn.setBackground(Colors.BASE_BTN_BG_COLOR);
    createNewProjectBtn.setForeground(Color.WHITE);
    createNewProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
    createNewProjectBtn.addActionListener(this::openCreateProjectFrameAction);

    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.gridwidth = 1;
    gridConstraints.insets = new Insets(0, 0, 0, 10);
    contentPanel.add(createNewProjectBtn, gridConstraints);

    joinToProjectBtn = new JButton("Join to Project");
    joinToProjectBtn.setFocusable(false);
    joinToProjectBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    joinToProjectBtn.setVerticalAlignment(JButton.CENTER);
    joinToProjectBtn.setHorizontalAlignment(JButton.CENTER);
    joinToProjectBtn.setSize(100, 200);
    joinToProjectBtn.setBackground(Colors.BASE_BTN_BG_COLOR);
    joinToProjectBtn.setForeground(Color.WHITE);
    joinToProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

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
}
