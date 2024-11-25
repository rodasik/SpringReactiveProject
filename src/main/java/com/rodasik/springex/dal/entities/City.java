package com.rodasik.springex.dal.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "cities")
@Getter
@Setter
@RequiredArgsConstructor
public class City extends BaseEntity {
    private String name;
    private String code;
    private String description;
    private long population;
    private double surfaceArea;
    private UUID parentId;
}
