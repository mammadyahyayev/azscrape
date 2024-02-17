package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.*;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.Fonts;
import az.caspian.core.remote.Project;
import az.caspian.core.service.ProjectService;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;


public class ShareProjectFrame extends DefaultFrame {
  private final ProjectService projectService;

  private DefaultButton shareProjectBtn;

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

    List<String[]> projectsTableDate = IntStream.range(0, projectService.getAllProjectNames().size())
      .boxed()
      .map(index -> {
        String project = projectService.getAllProjectNames().get(index);
        return new String[]{String.valueOf(index + 1), project, ""};
      })
      .toList();

    var projectsTable = new DefaultTable(TableColumnName.class, projectsTableDate.toArray(new String[0][]));
    projectsPanel.add(new JScrollPane(projectsTable), BorderLayout.CENTER);
    projectsTable.makeColumnEditable(TableColumnName.ACTIONS.ordinal());
    projectsTable.getColumn(TableColumnName.ACTIONS.getName()).setCellRenderer(getTableActions());

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
      else actionsPanel.setBackground(table.getBackground());

      shareProjectBtn = new DefaultButton("Share");
      shareProjectBtn.setBackground(new Color(0x1E89D6));
      shareProjectBtn.setMargin(5);
      shareProjectBtn.addActionListener(e -> {
        var selectedRow = table.getSelectedRow();
        var colIndexOfProject = table.getColumnModel().getColumnIndex(TableColumnName.PROJECT_NAME.getName());
        var selectedProject = (String) table.getModel().getValueAt(selectedRow, colIndexOfProject);
        Project sharedProject = projectService.shareProject(selectedProject);

        if (sharedProject != null)
          redirectToProjectViewFrame(sharedProject);
        else
          MessageBox.error("Failed to share project", this);
      });

      var editProjectBtn = new DefaultButton("Edit");
      editProjectBtn.setBackground(Colors.TBL_EDIT_BTN_COLOR);
      editProjectBtn.setMargin(5);

      var deleteProjectBtn = new DefaultButton("Delete");
      deleteProjectBtn.setBackground(Colors.TBL_DELETE_BTN_COLOR);
      deleteProjectBtn.setMargin(5);

      actionsPanel.add(shareProjectBtn);
      actionsPanel.add(editProjectBtn);
      actionsPanel.add(deleteProjectBtn);

      table.getColumnModel().getColumn(column).setCellEditor(new TableActionCellEditor(actionsPanel));
      return actionsPanel;
    };
  }

  private void redirectToProjectViewFrame(Project sharedProject) {
    this.dispose();
    new ProjectViewFrame(sharedProject);
  }

  public static class TableActionCellEditor extends DefaultCellEditor {
    private final JPanel actionsPanel;

    public TableActionCellEditor(JPanel actionsPanel) {
      super(new JCheckBox());
      this.actionsPanel = actionsPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      actionsPanel.setBackground(table.getSelectionBackground());
      return actionsPanel;
    }
  }

  enum TableColumnName implements TableColumn {
    ROW_NUM("RowNum"),
    PROJECT_NAME("Project Name"),
    ACTIONS("Actions");

    private final String name;

    TableColumnName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
