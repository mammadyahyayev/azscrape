package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.*;
import az.caspian.client.ui.constants.Colors;
import az.caspian.client.ui.constants.Fonts;
import az.caspian.core.messaging.Client;
import az.caspian.core.remote.Project;
import az.caspian.core.remote.Session;
import az.caspian.core.task.TaskManager;
import az.caspian.core.template.TemplateExecutor;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.IntStream;

import static javax.swing.SwingConstants.CENTER;


public class ProjectViewFrame extends JFrame {
  private JButton seeConfigFileBtn;
  private JButton shareProjectTasksBtn;

  private DefaultButton editMemberBtn;
  private DefaultButton deleteMemberBtn;

  private Project project;

  public ProjectViewFrame() {
    super();
    initUi();
  }

  public ProjectViewFrame(Project project) {
    super();
    this.project = project;

    initUi();
  }

  private void initUi() {
    this.getContentPane().setBackground(Colors.BASE_BG_COLOR);
    this.setSize(700, 600);
    this.setLocation(600, 200);

    loadUiComponents();

    this.setVisible(true);
  }

  private void loadUiComponents() {
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

    var projectNameValueLbl = new JLabel(project.getName());
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

    var projectOwner = project.getCreatedBy();

    var projectOwnerValueLbl = new JLabel();
    if (projectOwner != null) {
      projectOwnerValueLbl.setText(projectOwner.getFullName());
      projectOwnerValueLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
      projectOwnerValueLbl.setForeground(Color.WHITE);
    }

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

    var projectOperationsPanel = new JPanel();
    projectOperationsPanel.setBackground(Colors.BASE_BG_COLOR);

    seeConfigFileBtn = new DefaultButton("Project Config");
    projectOperationsPanel.add(seeConfigFileBtn);

    shareProjectTasksBtn = new DefaultButton("Share");
    shareProjectTasksBtn.addActionListener(this::shareTasksBetweenClients);
    projectOperationsPanel.add(shareProjectTasksBtn);

    gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 2;
    gridConstraints.weightx = 1;
    gridConstraints.fill = GridBagConstraints.WEST;
    gridConstraints.anchor = GridBagConstraints.LINE_START;
    gridConstraints.insets = new Insets(0, 5, 0, 0);

    projectDescriptionPanel.add(projectOperationsPanel, gridConstraints);

    contentPanel.add(projectDescriptionPanel, BorderLayout.NORTH);

    //#endregion

    //#region Project Members Panel
    var projectMembersPanel = new JPanel();
    projectMembersPanel.setLayout(new BorderLayout(0, 20));
    projectMembersPanel.setBackground(Colors.BASE_BG_COLOR);

    var projectMembersLbl = new JLabel("Project Members");
    projectMembersLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
    projectMembersLbl.setHorizontalAlignment(CENTER);
    projectMembersLbl.setForeground(Color.WHITE);

    projectMembersPanel.add(projectMembersLbl, BorderLayout.NORTH);

    List<String[]> list = IntStream.range(0, project.getAttendants().size()).boxed()
      .map(index -> {
        Client client = project.getAttendants().get(index);
        return new String[]{String.valueOf(index + 1), client.getFullName(), ""};
      })
      .toList();

    var membersTable = new DefaultTable(TableColumnName.class, list.toArray(new String[0][]));
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
      editMemberBtn.setBackground(Colors.TBL_EDIT_BTN_COLOR);

      deleteMemberBtn = new DefaultButton("Delete");
      deleteMemberBtn.setFont(Fonts.SANS_SERIF_PLAIN_16);
      deleteMemberBtn.setBackground(Colors.TBL_DELETE_BTN_COLOR);

      actionsPanel.add(editMemberBtn);
      actionsPanel.add(deleteMemberBtn);

      return actionsPanel;
    };
  }

  enum TableColumnName implements TableColumn {
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
  }

  private void shareTasksBetweenClients(ActionEvent event) {
    Client taskSender = Session.getCurrentClient();

    ServiceLoader<TemplateExecutor> templateExecutors = ServiceLoader.load(TemplateExecutor.class);
    Optional<TemplateExecutor> optionalTemplateExecutor = templateExecutors.findFirst();
    if (optionalTemplateExecutor.isEmpty()) {
      throw new IllegalStateException("Can't find TemplateExecutor implementation to execute task!");
    }

    var taskManager = new TaskManager(optionalTemplateExecutor.get());

    //TODO: divide tasks from scrape template
    taskManager.sendTasks(taskSender, Collections.emptyList());
  }

}
