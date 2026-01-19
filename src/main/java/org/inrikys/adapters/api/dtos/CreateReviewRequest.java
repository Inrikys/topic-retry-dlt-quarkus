package org.inrikys.adapters.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.inrikys.domain.models.Review;

public record CreateReviewRequest(
        @JsonProperty("userId")
        String userId,

        @JsonProperty("rating")
        String rating,

        @JsonProperty("commentary")
        String commentary
) {

    public Review toReview(String productId) {
        return null;
    }

}
