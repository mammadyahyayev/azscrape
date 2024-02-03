package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.DefaultFrame;
import az.caspian.client.ui.components.DefaultLabel;
import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.Fonts;
import az.caspian.core.service.ProjectService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ShareProjectFrame extends DefaultFrame {
  private final ProjectService projectService;

  public ShareProjectFrame(ProjectService projectService) {
    super();

    this.projectService = projectService;

    this.setSize(600, 400);

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
    contentPanel.setBackground(Colors.BASE_BG_COLOR);
    contentPanel.setLayout(new GridBagLayout());

    var myProjectsLbl = new DefaultLabel("My Projects");
    myProjectsLbl.setHorizontalAlignment(SwingConstants.CENTER);
    myProjectsLbl.setFont(Fonts.SANS_SERIF_BOLD_20);

    var constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = 2;
    constraints.insets = new Insets(20, 0, 10, 0);

    contentPanel.add(myProjectsLbl, constraints);

    var projectsPanel = new JPanel();
    projectsPanel.setLayout(new BorderLayout());
    projectsPanel.setBackground(Colors.BASE_BG_COLOR);

    var columnNames = new String[]{"RowNum", "Project Name", "Actions"};
    var data = new String[][]{
      {"1", "Solidia", "", ""},
      {"2", "Bina.az", "", ""},
    };
    var projectsTable = new JTable(data, columnNames);
    projectsPanel.add(new JScrollPane(projectsTable), BorderLayout.CENTER);

    projectsTable.getTableHeader().setReorderingAllowed(false);
    projectsTable.getTableHeader().setBackground(new Color(0x00E7FF));
    projectsTable.getTableHeader().setOpaque(false);
    projectsTable.getTableHeader().setForeground(Color.WHITE);
    projectsTable.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

    projectsTable.setBackground(Colors.BASE_BG_COLOR);
    projectsTable.setForeground(Color.WHITE);
    projectsTable.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    projectsTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    projectsTable.setFocusable(false);
    projectsTable.setIntercellSpacing(new Dimension(0, 0));
    projectsTable.setRowHeight(40);
    projectsTable.setSelectionBackground(new Color(0x50C2D3));

    projectsTable.getColumn("Actions").setCellRenderer(getTableActions());

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1.0;
    constraints.weighty = 1.0;
    constraints.insets = new Insets(0, 0, 10, 0);

    contentPanel.add(projectsPanel, constraints);

    return contentPanel;
  }

  private TableCellRenderer getTableActions() {
    return (table, value, isSelected, hasFocus, row, column) -> {
      var actionsPanel = new JPanel();

      if (isSelected) actionsPanel.setBackground(table.getSelectionBackground());
      else actionsPanel.setBackground(Colors.BASE_BG_COLOR);

      actionsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

      var shareProjectBtn = new JButton("Share");
      shareProjectBtn.setFocusable(false);
      shareProjectBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
      shareProjectBtn.setBackground(new Color(0x1E89D6));
      shareProjectBtn.setForeground(Color.WHITE);
      shareProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

      var editProjectBtn = new JButton("Edit");
      editProjectBtn.setFocusable(false);
      editProjectBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
      editProjectBtn.setBackground(new Color(0xFDC402));
      editProjectBtn.setForeground(Color.WHITE);
      editProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

      var deleteProjectBtn = new JButton("Delete");
      deleteProjectBtn.setFocusable(false);
      deleteProjectBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
      deleteProjectBtn.setBackground(Color.RED);
      deleteProjectBtn.setForeground(Color.WHITE);
      deleteProjectBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

      actionsPanel.add(shareProjectBtn);
      actionsPanel.add(editProjectBtn);
      actionsPanel.add(deleteProjectBtn);

      return actionsPanel;
    };
  }
}
