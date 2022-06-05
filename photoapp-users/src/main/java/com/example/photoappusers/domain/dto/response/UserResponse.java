package com.example.photoappusers.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponse {
    @JsonProperty("user_id")
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
}
