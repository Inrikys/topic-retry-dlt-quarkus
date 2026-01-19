package org.inrikys.adapters.api.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.inrikys.adapters.api.dtos.CreateReviewRequest;
import org.inrikys.domain.services.CreateReview;

import java.net.URI;
import java.util.UUID;

@Path("/products/{id}/reviews")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreateReviewController {

    private final CreateReview createReview;

    public CreateReviewController(CreateReview createReview) {
        this.createReview = createReview;
    }


    @POST
    public Response createReview(@PathParam("id") String productId, CreateReviewRequest request, @Context UriInfo uriInfo) {

        URI location = uriInfo.getAbsolutePathBuilder()
                .path(UUID.randomUUID().toString())
                .build();

        return Response.created(location).build();

    }

}
