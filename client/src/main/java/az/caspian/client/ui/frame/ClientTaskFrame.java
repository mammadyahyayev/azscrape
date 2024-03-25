package az.caspian.client.ui.frame;

import az.caspian.client.ui.components.*;
import az.caspian.client.ui.constants.Colors;
import az.caspian.core.task.Task;
import az.caspian.core.task.TaskManager;
import az.caspian.core.template.TemplateExecutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.ServiceLoader;

public class ClientTaskFrame extends DefaultFrame {

  private final Task task;

  public ClientTaskFrame(Task task) {
    super();
    this.task = task;

    this.setSize(400, 400);

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

    var taskLabel = new DefaultLabel("Task ID: " + task.getId());

    var gridConstraints = new GridBagConstraints();
    gridConstraints.gridx = 0;
    gridConstraints.gridy = 0;
    gridConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridConstraints.gridwidth = 2;
    gridConstraints.insets = new Insets(0, 0, 10, 0);

    contentPanel.add(taskLabel, gridConstraints);

    var executeTaskBtn = new DefaultButton("Execute Task");
    executeTaskBtn.setActionListener(this::executeTaskAction);
    gridConstraints.gridy = 1;
    contentPanel.add(executeTaskBtn, gridConstraints);

    return contentPanel;
  }

  private void executeTaskAction(ActionEvent event) {
    ServiceLoader<TemplateExecutor> templateExecutors = ServiceLoader.load(TemplateExecutor.class);
    Optional<TemplateExecutor> optionalTemplateExecutor = templateExecutors.findFirst();
    if (optionalTemplateExecutor.isEmpty()) {
      throw new IllegalStateException("Can't find TemplateExecutor implementation to execute task!");
    }

    TaskManager manager = new TaskManager(optionalTemplateExecutor.get());
    manager.executeTask(task);
  }
}
