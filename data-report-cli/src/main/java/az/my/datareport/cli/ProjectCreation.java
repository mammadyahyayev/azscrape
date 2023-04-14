package az.my.datareport.cli;

import az.my.datareport.config.Owner;
import az.my.datareport.config.Project;
import az.my.datareport.services.OwnerServiceImpl;
import az.my.datareport.services.ProjectService;
import az.my.datareport.utils.PropertiesFileSystem;

public class ProjectCreation implements Creation {

    private final ProjectService projectService;
    private final ConsoleReader reader;
    private final Logs logs;

    public ProjectCreation(ProjectService projectService, ConsoleReader reader, Logs logs) {
        this.projectService = projectService;
        this.reader = reader;
        this.logs = logs;
    }

    @Override
    public void create() {
        Project project = new Project();

        Step<Project> projectInfoStep = new ProjectInfoStep(reader);
        project = projectInfoStep.execute(project);

        Step<Owner> ownerStep = new ProjectOwnerStep(new OwnerServiceImpl(new PropertiesFileSystem()), reader);
        Owner owner = new Owner();
        owner = ownerStep.execute(owner);
        project.setOwner(owner);

        projectService.createProject(project);

        logs.info("Project created successfully...");
    }
}
