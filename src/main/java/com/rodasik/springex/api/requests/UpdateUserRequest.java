package com.rodasik.springex.api.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserRequest {
    @NotNull
    private UUID id;

    @NotEmpty(message = "Email cannot be empty!")
    private String email;

    @NotEmpty(message = "First name cannot be empty!")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty!")
    private String lastName;

    @NotEmpty(message = "Username cannot be empty!")
    private String username;
}
