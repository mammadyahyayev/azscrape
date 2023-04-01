package az.my.datareport.cli;

import az.my.datareport.config.Owner;
import az.my.datareport.config.Project;
import az.my.datareport.services.ProjectService;

public class ProjectCreation implements CreationStep<Project> {

    private final ProjectService projectService;
    private final ConsoleReader reader;
    private final Logs logs;

    public ProjectCreation(ProjectService projectService, ConsoleReader reader, Logs logs) {
        this.projectService = projectService;
        this.reader = reader;
        this.logs = logs;
    }

    @Override
    public Project start() {
        Project project = new Project();

        Step<Project> projectInfoStep = new ProjectInfoStep(reader);
        project = projectInfoStep.execute(project);

        // TODO: Check there is a default owner or not, if yes then accept owner
        Step<Owner> ownerStep = new ProjectOwnerStep(reader);
        Owner owner = new Owner();
        owner = ownerStep.execute(owner);
        project.setOwner(owner);

        projectService.createProject(project);

        logs.info("Project created successfully...");
        return project;
    }
}
