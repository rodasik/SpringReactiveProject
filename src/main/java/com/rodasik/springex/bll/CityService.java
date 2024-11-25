package com.rodasik.springex.bll;

import com.rodasik.springex.api.requests.CreateCityRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateCityRequest;
import com.rodasik.springex.dal.entities.City;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CityService {
    Flux<City> getCitiesByCountryId(UUID countryId);
    Flux<City> getCities(SearchRequest query);
    Mono<Page<City>> searchCities(SearchRequest query);
    Mono<City> createCity(CreateCityRequest country);
    Mono<City> updateCity(UpdateCityRequest country);
    Mono<Void> deleteCityById(UUID id);
}
