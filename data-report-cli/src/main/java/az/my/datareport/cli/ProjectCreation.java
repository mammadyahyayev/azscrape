package az.my.datareport.cli;

import az.my.datareport.config.DataReportProjectConfiguration;
import az.my.datareport.config.Owner;
import az.my.datareport.config.Project;

public class ProjectCreation implements CreationStep<Project> {

    private final DataReportProjectConfiguration configuration;
    private final ConsoleReader reader;
    private final Logs logs;

    public ProjectCreation(DataReportProjectConfiguration configuration, ConsoleReader reader, Logs logs) {
        this.configuration = configuration;
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

        configuration.createProject(project);

        logs.info("Project created successfully...");
        return project;
    }
}
