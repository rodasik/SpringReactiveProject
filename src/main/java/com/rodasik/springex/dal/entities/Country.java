package com.rodasik.springex.dal.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "countries")
@Getter
@Setter
@RequiredArgsConstructor
public class Country extends BaseEntity {
    private String name;
    private String code;
    @ReadOnlyProperty
    @DocumentReference
    private List<City> cities;
}
