package az.caspian.client.ui;

import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.UiConstants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProjectViewFrame extends JFrame {
  private JButton seeConfigFileBtn;

  private JButton editMemberBtn;
  private JButton deleteMemberBtn;

  public ProjectViewFrame() {
    this.setTitle(UiConstants.MAIN_FRAME_TITLE);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    this.getContentPane().setBackground(Colors.BASE_BG_COLOR);
    this.setSize(700, 800);
    this.setLocation(600, 200);

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
    contentPanel.setLayout(new BorderLayout(0, 10));
    contentPanel.setBackground(Colors.BASE_BG_COLOR);

    //#region Project Description Panel
    var projectDescriptionPanel = new JPanel();
    projectDescriptionPanel.setLayout(new GridBagLayout());
    projectDescriptionPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectNamePanel = new JPanel();
    projectNamePanel.setBackground(Colors.BASE_BG_COLOR);

    var projectNameLbl = new JLabel("Project Name: ");
    projectNameLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
    projectNameLbl.setForeground(Color.WHITE);

    var projectNameValueLbl = new JLabel("turbo.az scraping");
    projectNameValueLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    projectNameValueLbl.setForeground(Color.WHITE);

    projectNamePanel.add(projectNameLbl);
    projectNamePanel.add(projectNameValueLbl);

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.WEST;
    gridConstraints.weightx = 1;
    gridConstraints.anchor = GridBagConstraints.LINE_START;
    gridConstraints.insets = new Insets(0, 5, 10, 0);

    projectDescriptionPanel.add(projectNamePanel, gridConstraints);

    var projectOwnerPanel = new JPanel();
    projectOwnerPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectOwnerLbl = new JLabel("Project Owner: ");
    projectOwnerLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
    projectOwnerLbl.setForeground(Color.WHITE);

    var projectOwnerValueLbl = new JLabel("Mammad Yahyayev");
    projectOwnerValueLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    projectOwnerValueLbl.setForeground(Color.WHITE);

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 1;
    gridConstraints.fill = GridBagConstraints.WEST;
    gridConstraints.weightx = 1;
    gridConstraints.anchor = GridBagConstraints.LINE_START;
    gridConstraints.insets = new Insets(0, 5, 10, 0);

    projectOwnerPanel.add(projectOwnerLbl);
    projectOwnerPanel.add(projectOwnerValueLbl);

    projectDescriptionPanel.add(projectOwnerPanel, gridConstraints);

    var projectConfigPanel = new JPanel();
    projectConfigPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectConfigLbl = new JLabel("Project Config: ");
    projectConfigLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
    projectConfigLbl.setForeground(Color.WHITE);

    projectConfigPanel.add(projectConfigLbl);

    seeConfigFileBtn = new JButton("See Config file");
    seeConfigFileBtn.setFocusable(false);
    seeConfigFileBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
    seeConfigFileBtn.setBackground(Colors.BASE_BTN_BG_COLOR);
    seeConfigFileBtn.setForeground(Color.WHITE);
    seeConfigFileBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

    projectConfigPanel.add(seeConfigFileBtn);

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 2;
    gridConstraints.weightx = 1;
    gridConstraints.fill = GridBagConstraints.WEST;
    gridConstraints.anchor = GridBagConstraints.LINE_START;
    gridConstraints.insets = new Insets(0, 5, 0, 0);

    projectDescriptionPanel.add(projectConfigPanel, gridConstraints);

    contentPanel.add(projectDescriptionPanel, BorderLayout.NORTH);

    //#endregion

    //#region Project Members Panel
    var projectMembersPanel = new JPanel();
    projectMembersPanel.setLayout(new BorderLayout(0, 20));
    projectMembersPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectMembersLbl = new JLabel("Project Members");
    projectMembersLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    projectMembersLbl.setHorizontalAlignment(JLabel.CENTER);
    projectMembersLbl.setForeground(Color.WHITE);

    projectMembersPanel.add(projectMembersLbl, BorderLayout.NORTH);

    var columnNames = new String[]{"Id", "Name", "Computer", "Actions"};
    var data =
      new String[][]{
        {"1", "Jack", "Jackson", ""},
        {"2", "John", "Doe", ""},
        {"3", "Smith", "Machine", ""},
      };
    var membersTable = new JTable(data, columnNames);
    projectMembersPanel.add(new JScrollPane(membersTable));

    membersTable.getTableHeader().setReorderingAllowed(false);
    membersTable.getTableHeader().setBackground(new Color(0x00E7FF));
    membersTable.getTableHeader().setOpaque(false);
    membersTable.getTableHeader().setForeground(Color.WHITE);
    membersTable.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

    membersTable.setBackground(Colors.BASE_BG_COLOR);
    membersTable.setForeground(Color.WHITE);
    membersTable.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    membersTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    membersTable.setFocusable(false);
    membersTable.setIntercellSpacing(new Dimension(0, 0));
    membersTable.setRowHeight(40);
    membersTable.setSelectionBackground(new Color(0x50C2D3));

    membersTable.getColumn("Actions").setCellRenderer(getTableActions());

    contentPanel.add(projectMembersPanel, BorderLayout.CENTER);
    //#endregion

    return contentPanel;
  }

  private TableCellRenderer getTableActions() {
    return (table, value, isSelected, hasFocus, row, column) -> {
      var actionsPanel = new JPanel();

      if (isSelected) actionsPanel.setBackground(table.getSelectionBackground());
      else actionsPanel.setBackground(Colors.BASE_BG_COLOR);

      actionsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

      editMemberBtn = new JButton("Edit");
      editMemberBtn.setFocusable(false);
      editMemberBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
      editMemberBtn.setBackground(new Color(0xFDC402));
      editMemberBtn.setForeground(Color.WHITE);
      editMemberBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

      deleteMemberBtn = new JButton("Delete");
      deleteMemberBtn.setFocusable(false);
      deleteMemberBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
      deleteMemberBtn.setBackground(Color.RED);
      deleteMemberBtn.setForeground(Color.WHITE);
      deleteMemberBtn.setBorder(new EmptyBorder(5, 5, 5, 5));

      actionsPanel.add(editMemberBtn);
      actionsPanel.add(deleteMemberBtn);

      return actionsPanel;
    };
  }
}
