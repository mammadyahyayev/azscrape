package az.my.datareport.cli;

import az.my.datareport.config.Project;

public class ProjectInfoStep implements Step<Project> {

    private final ConsoleReader reader;

    public ProjectInfoStep(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public Project execute(Project project) {
        String projectName = reader.readLine("Enter project name:", 3,
                "Project name cannot be null or empty");
        project.setName(projectName.trim());
        return project;
    }
}
