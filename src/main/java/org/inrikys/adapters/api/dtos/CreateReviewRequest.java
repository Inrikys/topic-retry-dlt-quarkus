package org.inrikys.adapters.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.inrikys.domain.models.Review;

public record CreateReviewRequest(
        @JsonProperty("userId")
        Long userId,

        @JsonProperty("rating")
        Integer rating,

        @JsonProperty("commentary")
        String commentary
) {

    public Review toReview(Long productId) {
        return new Review(null, productId, userId, rating, commentary);
    }

}
