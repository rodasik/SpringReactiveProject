package com.rodasik.springex.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper=true)
public class CityDTO extends ExtendedBaseDTO {
    private String description;
    private long population;
    private double surfaceArea;
    private UUID parentId;
}
