package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.DefaultButton;
import az.caspian.client.ui.components.DefaultTable;
import az.caspian.client.ui.components.FooterPanel;
import az.caspian.client.ui.components.HeaderPanel;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Arrays;


public class ProjectViewFrame extends JFrame {
  private JButton seeConfigFileBtn;

  private DefaultButton editMemberBtn;
  private DefaultButton deleteMemberBtn;

  public ProjectViewFrame() {
    super();

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
    projectNameLbl.setFont(Fonts.SANS_SERIF_BOLD_18);
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

    var data = new String[][]{
      {"1", "Jack", "Jackson", ""},
      {"2", "John", "Doe", ""},
      {"3", "Smith", "Machine", ""},
    };
    var membersTable = new DefaultTable(TableColumnName.columnNames(), data);
    projectMembersPanel.add(new JScrollPane(membersTable));

    membersTable.makeColumnEditable(TableColumnName.ACTIONS.ordinal());
    membersTable.getColumn(TableColumnName.ACTIONS.getName()).setCellRenderer(getTableActions());

    contentPanel.add(projectMembersPanel, BorderLayout.CENTER);
    //#endregion

    return contentPanel;
  }

  private TableCellRenderer getTableActions() {
    return (table, value, isSelected, hasFocus, row, column) -> {
      var actionsPanel = new JPanel();

      if (isSelected) actionsPanel.setBackground(table.getSelectionBackground());
      else actionsPanel.setBackground(table.getBackground());

      editMemberBtn = new DefaultButton("Edit");
      editMemberBtn.setFont(Fonts.SANS_SERIF_PLAIN_16);
      editMemberBtn.setBackground(new Color(0xFDC402));

      deleteMemberBtn = new DefaultButton("Delete");
      deleteMemberBtn.setFont(Fonts.SANS_SERIF_PLAIN_16);
      deleteMemberBtn.setBackground(Color.RED);

      actionsPanel.add(editMemberBtn);
      actionsPanel.add(deleteMemberBtn);

      return actionsPanel;
    };
  }


  enum TableColumnName {
    ROW_NUM("RowNum"),
    FULL_NAME("Full Name"),
    ACTIONS("Actions");

    private final String name;

    TableColumnName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public static String[] columnNames() {
      return Arrays.stream(values()).map(TableColumnName::getName).toArray(String[]::new);
    }
  }
}
