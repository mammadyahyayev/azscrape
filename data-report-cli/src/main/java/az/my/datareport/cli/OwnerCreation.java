package az.my.datareport.cli;

import az.my.datareport.config.Owner;
import az.my.datareport.services.OwnerService;

public class OwnerCreation implements CreationStep<Owner> {

    private final OwnerService ownerService;
    private final ConsoleReader reader;
    private final Logs logs;

    public OwnerCreation(OwnerService ownerService, ConsoleReader reader, Logs logs) {
        this.ownerService = ownerService;
        this.reader = reader;
        this.logs = logs;
    }

    @Override
    public Owner start() {
        Owner owner = new Owner();

        Step<Owner> ownerStep = new ProjectOwnerStep(reader);
        owner = ownerStep.execute(owner);

        ownerService.createOwner(owner);

        logs.info("Owner created successfully...");
        return null;
    }
}
