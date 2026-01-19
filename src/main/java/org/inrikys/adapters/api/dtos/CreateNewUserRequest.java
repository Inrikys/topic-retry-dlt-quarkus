package org.inrikys.adapters.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.inrikys.domain.models.User;

public record CreateNewUserRequest(
        @JsonProperty("name")
        String name,
        @JsonProperty("email")
        String email
) {
    public User toUser() {
        return new User(name, email);
    }
}
