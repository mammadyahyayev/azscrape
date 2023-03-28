package az.my.datareport.cli;

import az.my.datareport.config.Owner;

public class ProjectOwnerStep implements Step<Owner> {

    private final ConsoleReader reader;

    public ProjectOwnerStep(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public Owner execute(Owner owner) {
        String username = reader.readLine("Enter project owner username:", 3,
                "Owner username cannot be null or empty");
        owner.setName(username.trim());
        return owner;
    }
}
