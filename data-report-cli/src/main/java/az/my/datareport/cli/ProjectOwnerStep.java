package az.my.datareport.cli;

import az.my.datareport.config.Owner;
import az.my.datareport.services.OwnerService;

import java.text.MessageFormat;
import java.util.Optional;

public class ProjectOwnerStep implements Step<Owner> {

    private final OwnerService ownerService;
    private final ConsoleReader reader;

    public ProjectOwnerStep(OwnerService ownerService, ConsoleReader reader) {
        this.ownerService = ownerService;
        this.reader = reader;
    }

    @Override
    public Owner execute(Owner owner) {
        Optional<Owner> defaultOwner = ownerService.getOwner();
        if (defaultOwner.isPresent()) {
            String message = MessageFormat.format("There is already a default owner '{0}', " +
                    "If you want to accept as an owner of this project, please type (y/n): ", defaultOwner.get().getName());
            char choice = reader.readYesNoChoice(message, 3);
            if (choice == ConsoleReader.Choice.YES.getChoice()) {
                return defaultOwner.get();
            }
        }

        String username = reader.readLine("Enter project owner username: ", 3,
                "Owner username cannot be null or empty");
        owner.setName(username.trim());

        String email = reader.readLine("Enter your email (optional): ");
        owner.setEmail(email); //TODO: Use regex to validate or create additional layer for validation

        owner.setDefault(true);
        return owner;
    }
}
