package org.inrikys.domain.services;

import jakarta.transaction.Transactional;
import org.inrikys.domain.models.User;
import org.inrikys.domain.ports.CreateNewUserPort;

public class CreateNewUser {

    private final CreateNewUserPort createNewUserPort;

    public CreateNewUser(CreateNewUserPort createNewUserPort) {
        this.createNewUserPort = createNewUserPort;
    }

    public User create(User newUser) {
        return createNewUserPort.saveNewUser(newUser);
    }

}
