package com.rodasik.springex.api.handlers;

import com.rodasik.springex.api.dto.CountryDTO;
import com.rodasik.springex.api.mappers.CountryMapper;
import com.rodasik.springex.api.pagination.PaginatedResponse;
import com.rodasik.springex.api.requests.CreateCountryRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateCountryRequest;
import com.rodasik.springex.bll.CountryService;
import com.rodasik.springex.common.ValidationUtil;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CountryHandler {
    private final CountryService countryService;
    private final CountryMapper countryMapper;
    private final Validator validator;

    public Mono<ServerResponse> getCountries(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(countryService.getCountries().map(countryMapper::toDTO), CountryDTO.class);
    }

    public Mono<ServerResponse> searchCountries(ServerRequest request) {
        return request.bodyToMono(SearchRequest.class)
                .flatMap(req->ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(countryService.searchCountries(req)
                                .map(countryMapper::mapPaginatedResponse), PaginatedResponse.class));
    }

    public Mono<ServerResponse> createCountry(ServerRequest request) {
        return request.bodyToMono(CreateCountryRequest.class)
                .doOnNext(dto -> ValidationUtil.validate(dto, validator))
                .flatMap(countryService::createCountry)
                .flatMap(createdData -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(countryMapper.toDTO(createdData)));
    }

    public Mono<ServerResponse> updateCountry(ServerRequest request) {
        return request.bodyToMono(UpdateCountryRequest.class)
                .doOnNext(dto -> ValidationUtil.validate(dto, validator))
                .flatMap(countryService::updateCountry)
                .flatMap(updatedEntity -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(countryMapper.toDTO(updatedEntity)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCountry(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return countryService.deleteCountryById(id)
                .then(ServerResponse
                        .ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue("Deleted country with id: " + id))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
