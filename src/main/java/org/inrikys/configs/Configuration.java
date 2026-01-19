package org.inrikys.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.inrikys.domain.ports.CreateNewUserPort;
import org.inrikys.domain.services.CreateNewUser;

public class Configuration {

    @Produces
    @ApplicationScoped
    public CreateNewUser createNewUser(CreateNewUserPort createNewUserPort) {
        return new CreateNewUser(createNewUserPort);
    }

}
