package com.rodasik.springex.api.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreateCountryRequest extends BaseRequest {
    private UUID id;
}
