package org.inrikys.domain.ports;

import org.inrikys.domain.models.User;

import java.util.Optional;

public interface GetUserByEmailPort {

    Optional<User> getUser(String userId);

}
