package com.rodasik.springex.dal.repositories;

import com.rodasik.springex.dal.entities.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CountryRepository extends ReactiveMongoRepository<Country, UUID> {
    @Query("{ id:  { $exists: true }," +
            " $or: [" +
            "   { name: { $regex:  '(?i)?0(?-i)'}}," +
            "   { code: { $regex:  '(?i)?0(?-i)'}}]}")
    Flux<Country> getPaginatedCountriesWithFilter(String filter, final Pageable page);

    @Query("{ id: { $exists: true }}")
    Flux<Country> getPaginatedCountries(final Pageable page);

    Mono<Long> countByNameContainsOrCodeContains(String name, String code);
}
