package com.rodasik.springex.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreateCityRequest extends BaseRequest {
    private UUID id;

    private String description;

    private long population;

    private double surfaceArea;

    @NotNull(message = "Parent id cannot be null")
    private UUID parentId;
}
