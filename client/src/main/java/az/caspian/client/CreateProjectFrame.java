package az.caspian.client;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class CreateProjectFrame extends JFrame {
    private static final Border debugBorder = BorderFactory.createLineBorder(Color.RED, 3);

    private JButton createNewProjectBtn;

    public CreateProjectFrame() {
        this.setTitle("AZScrape Client");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.getContentPane().setBackground(new Color(0x0F172A));
        this.setSize(500, 400);
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
        contentPanel.setSize(450, 400);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(new Color(0x0F172A));

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


        createNewProjectBtn = new JButton("Create new Project");
        createNewProjectBtn.setFocusable(false);
        createNewProjectBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        createNewProjectBtn.setVerticalAlignment(JButton.CENTER);
        createNewProjectBtn.setHorizontalAlignment(JButton.CENTER);
        createNewProjectBtn.setBackground(new Color(0x1E727C));
        createNewProjectBtn.setForeground(Color.WHITE);
        createNewProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(0, 10, 0, 10);
        contentPanel.add(createNewProjectBtn, gridConstraints);

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
