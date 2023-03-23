package az.my.datareport.cli;

import az.my.datareport.config.Owner;
import az.my.datareport.config.Project;

import java.io.PrintStream;
import java.util.Scanner;

public class ProjectCreation implements CreationStep<Project> {

    private final Scanner scanner;
    private final PrintStream stdOut;
    private final PrintStream stdErr;
    private final ConsoleReader reader;

    public ProjectCreation(Scanner scanner, PrintStream stdOut, PrintStream stdErr,
                           ConsoleReader reader) {
        this.stdOut = stdOut;
        this.stdErr = stdErr;
        this.scanner = scanner;
        this.reader = reader;
    }

    @Override
    public Project start() {
        Project project = new Project();

        Step<Project> projectInfoStep = new ProjectInfoStep(reader);
        project = projectInfoStep.execute(project);

        projectOwner(project);

        stdOut.println("Project created successfully...");
        return project;
    }

    private void projectOwner(Project project) {
        stdOut.print("Please enter owner name: ");
        String name = scanner.next();
        if (name == null || name.isEmpty() || name.isBlank()) {
            stdErr.println("Owner name is required field and cannot be empty");
            projectOwner(project);
        } else {
            project.setOwner(new Owner(name, null));
        }
    }
}
