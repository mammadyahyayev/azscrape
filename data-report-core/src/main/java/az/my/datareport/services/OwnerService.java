package az.my.datareport.services;

import az.my.datareport.config.Owner;

public interface OwnerService {
    /**
     * Creates a new Owner.
     *
     * @param owner an {@code Owner}
     */
    void createOwner(Owner owner);

    /**
     * Retrieves Owner from <b>.ym.properties</b> file if there is one.
     *
     * @return owner if there is, otherwise {@code null}
     */
    Owner getOwner();
}
