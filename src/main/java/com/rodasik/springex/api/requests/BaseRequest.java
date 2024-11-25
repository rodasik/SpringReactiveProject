package com.rodasik.springex.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseRequest {
    @NotBlank(message = "Name cannot be blank!")
    private String name;

    @NotNull(message = "Code cannot be null!")
    private String code;
}
