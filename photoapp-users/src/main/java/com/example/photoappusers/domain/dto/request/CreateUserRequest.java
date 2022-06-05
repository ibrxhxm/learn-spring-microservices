package com.example.photoappusers.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequest {
    @NotNull(message = "firstname cannot be empty")
    private String firstname;

    @NotNull(message = "lastname cannot be empty")
    private String lastname;

    @Email
    @NotNull(message = "email cannot be empty")
    private String email;

    @NotNull(message = "firstname cannot be empty")
    @Size(min = 6, max = 10, message = "password must be not be less than 6 nor greater than 10 characters")
    private String password;
}
