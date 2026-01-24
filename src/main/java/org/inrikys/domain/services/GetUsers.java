package org.inrikys.domain.services;

import org.inrikys.domain.models.User;
import org.inrikys.domain.ports.GetUsersPort;

import java.util.List;

public class GetUsers {

    private final GetUsersPort getUsersPort;

    public GetUsers(GetUsersPort getUsersPort) {
        this.getUsersPort = getUsersPort;
    }

    public List<User> getUsers() {
        return getUsersPort.getUsers();
    }
}
