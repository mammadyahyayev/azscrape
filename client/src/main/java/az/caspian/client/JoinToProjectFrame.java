package az.caspian.client;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class JoinToProjectFrame extends JFrame {
  private static final Border debugBorder = BorderFactory.createLineBorder(Color.RED, 3);

  private JButton joinToProjectBtn;
  private JButton createNewProjectBtn;

  public JoinToProjectFrame() {
    this.setTitle("AZScrape Client");
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.getContentPane().setBackground(new Color(0x0F172A));
    this.setSize(400, 400);
    this.setLocation(700, 380);

    loadUi();

    this.setVisible(true);
  }

  private void loadUi() {
    JPanel headerPanel = createHeaderPanel();
    JPanel contentPanel = createContentPanel();
    JPanel footerPanel = createFooterPanel();

    this.add(headerPanel, BorderLayout.NORTH);
    this.add(contentPanel, BorderLayout.CENTER);
    this.add(footerPanel, BorderLayout.SOUTH);
  }

  private JPanel createHeaderPanel() {
    JPanel headerPanel = new JPanel();
    headerPanel.setSize(headerPanel.getWidth(), 50);
    headerPanel.setBackground(new Color(0x0F172A));

    JLabel headerLabel = new JLabel("AZScrape");
    headerLabel.setHorizontalTextPosition(JLabel.CENTER);
    headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    headerLabel.setForeground(Color.WHITE);

    headerPanel.add(headerLabel);
    return headerPanel;
  }

  private JPanel createContentPanel() {
    var contentPanel = new JPanel();
    contentPanel.setSize(300, 400);
    contentPanel.setLayout(new GridBagLayout());
    contentPanel.setBackground(new Color(0x0F172A));

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
    createNewProjectBtn.setBackground(new Color(0x1E727C));
    createNewProjectBtn.setForeground(Color.WHITE);
    createNewProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

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
    joinToProjectBtn.setBackground(new Color(0x1E727C));
    joinToProjectBtn.setForeground(Color.WHITE);
    joinToProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

    gridConstraints.gridx = 1;
    gridConstraints.gridy = 1;
    gridConstraints.gridwidth = 1;
    gridConstraints.insets = new Insets(0, 10, 0, 0);
    contentPanel.add(joinToProjectBtn, gridConstraints);

    return contentPanel;
  }

  private JPanel createFooterPanel() {
    JPanel footerPanel = new JPanel();
    footerPanel.setSize(footerPanel.getWidth(), 50);
    footerPanel.setBackground(new Color(0x0F172A));

    JLabel footerLabel = new JLabel("Copyright - Mammad Yahyayev");
    footerLabel.setHorizontalTextPosition(JLabel.CENTER);
    footerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    footerLabel.setForeground(Color.WHITE);

    footerPanel.add(footerLabel);
    return footerPanel;
  }
}
