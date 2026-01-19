package org.inrikys.domain.ports;

import org.inrikys.domain.models.User;

public interface CreateNewUserPort {

    User saveNewUser(User newUser);

}
