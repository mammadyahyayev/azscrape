package az.my.datareport.cli;

import az.my.datareport.config.DataReportProjectConfiguration;
import az.my.datareport.config.Owner;

public class OwnerCreation implements CreationStep<Owner> {

    private final DataReportProjectConfiguration configuration;
    private final ConsoleReader reader;
    private final Logs logs;

    public OwnerCreation(DataReportProjectConfiguration configuration, ConsoleReader reader, Logs logs) {
        this.configuration = configuration;
        this.reader = reader;
        this.logs = logs;
    }

    @Override
    public Owner start() {
        Owner owner = new Owner();

        Step<Owner> ownerStep = new ProjectOwnerStep(reader);
        owner = ownerStep.execute(owner);

        configuration.createOwner(owner);

        logs.info("Owner created successfully...");
        return null;
    }
}
