package com.rodasik.springex.bll;

import com.rodasik.springex.api.requests.CreateCountryRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateCountryRequest;
import com.rodasik.springex.dal.entities.Country;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CountryService {
    Flux<Country> getCountries();
    Mono<Page<Country>> searchCountries(SearchRequest query);
    Mono<Country> getCountryById(UUID id);
    Mono<Country> createCountry(CreateCountryRequest country);
    Mono<Country> updateCountry(UpdateCountryRequest country);
    Mono<Void> deleteCountryById(UUID id);
}
