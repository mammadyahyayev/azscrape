package az.my.datareport.services;

import az.my.datareport.config.Owner;
import az.my.datareport.utils.FileSystem;
import az.my.datareport.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Properties;

public class OwnerServiceImpl implements OwnerService {
    private static final Logger LOG = LogManager.getLogger(OwnerService.class);

    private final FileSystem fileSystem;

    public OwnerServiceImpl(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public void createOwner(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null!");
        }

        String configProperties = System.getProperty("config.properties.path");

        try (OutputStream outputStream = new FileOutputStream(configProperties)) {
            Properties properties = new Properties();
            properties.setProperty("owner.name", owner.getName());

            if (!StringUtils.isNullOrEmpty(owner.getEmail())) {
                properties.setProperty("owner.email", owner.getEmail());
            }

            properties.store(outputStream, "Global Properties");
            LOG.info("Owner created successfully...");
        } catch (IOException e) {
            LOG.error("Failed to create owner {}", e.getMessage());
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }

    @Override
    public Optional<Owner> getOwner() {
        String configProperties = System.getProperty("config.properties.path");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configProperties));

            String ownerName = properties.getProperty("owner.name");
            String ownerEmail = properties.getProperty("owner.email");
            if (ownerName == null) {
                return Optional.empty();
            }

            Owner owner = new Owner(ownerName, ownerEmail);
            owner.setDefault(true);

            return Optional.of(owner);
        } catch (IOException e) {
            String message = MessageFormat.format("Failed to read from file {0}", configProperties);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }
    }
}
