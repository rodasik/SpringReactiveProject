package com.rodasik.springex.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class CountryDTO extends ExtendedBaseDTO {
    private List<CityDTO> cities;
}
