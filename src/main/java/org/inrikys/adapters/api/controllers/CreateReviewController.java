package org.inrikys.adapters.api.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.inrikys.adapters.api.dtos.CreateReviewRequest;
import org.inrikys.domain.models.Review;
import org.inrikys.domain.services.CreateNewReview;

import java.net.URI;

@Path("/products/{id}/reviews")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreateReviewController {

    private final CreateNewReview createNewReview;

    public CreateReviewController(CreateNewReview createNewReview) {
        this.createNewReview = createNewReview;
    }


    @POST
    public Response createReview(@PathParam("id") String productId, CreateReviewRequest request, @Context UriInfo uriInfo) {

        Review review = request.toReview(Long.parseLong(productId));
        Review newReview = createNewReview.create(review);

        URI location = uriInfo.getAbsolutePathBuilder()
                .path("/products/" + productId + "/" + newReview.getId())
                .build();

        return Response.created(location).build();

    }

}
