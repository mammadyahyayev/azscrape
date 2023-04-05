package az.my.datareport.services;

import az.my.datareport.config.Owner;
import az.my.datareport.utils.PropertiesFileSystem;
import az.my.datareport.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class OwnerServiceImpl implements OwnerService {
    private static final Logger LOG = LogManager.getLogger(OwnerService.class);

    private final PropertiesFileSystem fileSystem;

    public OwnerServiceImpl(PropertiesFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public void createOwner(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null!");
        }

        String configProperties = System.getProperty("config.properties.path");

        Properties properties = fileSystem.load(configProperties);
        properties.setProperty("owner.name", owner.getName());

        if (!StringUtils.isNullOrEmpty(owner.getEmail())) {
            properties.setProperty("owner.email", owner.getEmail());
        }
        fileSystem.store(configProperties, properties);

        LOG.info("Owner created successfully...");
    }

    @Override
    public Owner getOwner() {
        String configProperties = System.getProperty("config.properties.path");

        Properties properties = fileSystem.load(configProperties);
        String ownerName = properties.getProperty("owner.name");
        String ownerEmail = properties.getProperty("owner.email");

        if (ownerName == null) {
            return null;
        }

        Owner owner = new Owner(ownerName, ownerEmail);
        owner.setDefault(true);

        return owner;
    }
}
