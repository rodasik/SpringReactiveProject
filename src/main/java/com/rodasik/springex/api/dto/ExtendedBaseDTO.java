package com.rodasik.springex.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ExtendedBaseDTO extends BaseDTO {
    private String name;
    private String code;
}
