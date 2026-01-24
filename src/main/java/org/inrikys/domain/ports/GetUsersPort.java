package org.inrikys.domain.ports;

import org.inrikys.domain.models.User;

import java.util.List;

public interface GetUsersPort {

    List<User> getUsers();

    Boolean existsById(Long userId);
}
