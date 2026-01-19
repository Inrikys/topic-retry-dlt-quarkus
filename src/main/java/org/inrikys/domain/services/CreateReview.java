package org.inrikys.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.inrikys.domain.models.Review;
import org.inrikys.domain.ports.GetProductByIdPort;
import org.inrikys.domain.ports.GetUserByEmailPort;
import org.inrikys.domain.ports.ReviewProductPort;

@ApplicationScoped
public class CreateReview {

    private GetUserByEmailPort getUserById;
    private GetProductByIdPort getProductById;
    private ReviewProductPort reviewProduct;

    public Review create(Review review) {

        return null;

    }

}
