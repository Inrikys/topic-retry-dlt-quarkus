package org.inrikys.domain.ports;

import org.inrikys.domain.models.Review;

public interface CreateNewReviewPort {

    Review createNewReview(Review review);

}
