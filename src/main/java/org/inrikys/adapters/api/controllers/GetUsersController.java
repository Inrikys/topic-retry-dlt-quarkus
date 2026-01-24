package org.inrikys.adapters.api.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.inrikys.domain.models.User;
import org.inrikys.domain.services.GetUsers;

import java.util.List;

@Path("/users")
public class GetUsersController {

    private final GetUsers getUsers;

    public GetUsersController(GetUsers getUsers) {
        this.getUsers = getUsers;
    }

    @GET
    public Response getUsers() {
        List<User> users = this.getUsers.getUsers();
        return Response.ok(users).build();
    }

}
