package com.rodasik.springex.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=true)
public class UpdateCountryRequest extends BaseRequest {
    @NotNull
    private UUID id;
}
