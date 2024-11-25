package com.rodasik.springex.dal.repositories;

import com.rodasik.springex.dal.entities.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CityRepository extends ReactiveMongoRepository<City, UUID> {
    @Query("{ id:  { $exists: true }," +
            " parentId: ?0," +
            " $or: [" +
            "   { name: { $regex:  '(?i)?0(?-i)'}}," +
            "   { code: { $regex:  '(?i)?0(?-i)'}}," +
            "   { description:  { $regex: '(?i)?0(?-i)'}}" +
            "]}")
    Flux<City> getPaginatedCitiesWithFilterAndCountryId(UUID parentId, String filter, final Pageable page);

    @Query("{ id: { $exists: true }, parentId:  ?0 }")
    Flux<City> getPaginatedCitiesWithCountryId(UUID parentId, final Pageable page);

    @Query("{ id:  { $exists: true }," +
            " $or: [" +
            "   { name: { $regex:  '(?i)?0(?-i)'}}," +
            "   { code: { $regex:  '(?i)?0(?-i)'}}," +
            "   { description:  { $regex: '(?i)?0(?-i)'}}" +
            "]}")
    Flux<City> getPaginatedCitiesWithFilter(String filter, final Pageable page);

    @Query("{ id:  { $exists: true } }")
    Flux<City> getPaginatedCities(final Pageable page);

    Mono<Long> countByNameContainsOrCodeContainsOrDescriptionContains(String name, String code, String description);
}