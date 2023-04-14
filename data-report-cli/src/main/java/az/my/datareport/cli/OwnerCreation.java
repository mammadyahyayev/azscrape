package az.my.datareport.cli;

import az.my.datareport.config.Owner;
import az.my.datareport.services.OwnerService;

public class OwnerCreation implements Creation {

    private final OwnerService ownerService;
    private final ConsoleReader reader;
    private final Logs logs;

    public OwnerCreation(OwnerService ownerService, ConsoleReader reader, Logs logs) {
        this.ownerService = ownerService;
        this.reader = reader;
        this.logs = logs;
    }

    @Override
    public void create() {
        Owner owner = new Owner();

        Step<Owner> ownerStep = new ProjectOwnerStep(ownerService, reader);
        owner = ownerStep.execute(owner);

        ownerService.createOwner(owner);

        logs.info("Owner created successfully...");
    }
}
