package az.my.datareport.config;

import java.time.LocalDateTime;

public class Project {
    private String name;
    private Owner owner;
    private final LocalDateTime createdAt;

    public Project() {
        this.createdAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
