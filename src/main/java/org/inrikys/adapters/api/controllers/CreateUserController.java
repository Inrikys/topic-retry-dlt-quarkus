package org.inrikys.adapters.api.controllers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.inrikys.adapters.api.dtos.CreateNewUserRequest;
import org.inrikys.domain.models.User;
import org.inrikys.domain.services.CreateNewUser;

import java.net.URI;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreateUserController {

    private final CreateNewUser createNewUser;

    public CreateUserController(CreateNewUser createNewUser) {
        this.createNewUser = createNewUser;
    }

    @POST
    public Response createNewUser(CreateNewUserRequest request, @Context UriInfo uriInfo) {

        User newUser = request.toUser();

        User user = createNewUser.create(newUser);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId()))
                .build();

        return Response.created(location).build();

    }

}
