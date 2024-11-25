package com.rodasik.springex.api.handlers;

import com.rodasik.springex.api.dto.CityDTO;
import com.rodasik.springex.api.mappers.CityMapper;
import com.rodasik.springex.api.pagination.PaginatedResponse;
import com.rodasik.springex.api.requests.CreateCityRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateCityRequest;
import com.rodasik.springex.bll.CityService;
import com.rodasik.springex.common.ValidationUtil;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CityHandler {
    private final CityService cityService;
    private final CityMapper cityMapper;
    private final Validator validator;

    @Autowired
    public CityHandler(CityService cityService, CityMapper cityMapper, Validator validator) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
        this.validator = validator;
    }

    public Mono<ServerResponse> getCitiesByCountryId(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(cityService.getCitiesByCountryId(
                        UUID.fromString(request.pathVariable("parentId"))
                ).map(cityMapper::toDTO), CityDTO.class);
    }

    public Mono<ServerResponse> getCities(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SearchRequest.class)
                .flatMap(request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(cityService.getCities(request).map(cityMapper::toDTO), CityDTO.class));
    }

    public Mono<ServerResponse> searchCities(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SearchRequest.class)
                .flatMap(request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(cityService.searchCities(request).map(cityMapper::mapPaginatedResponse), PaginatedResponse.class));
    }

    public Mono<ServerResponse> createCity(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateCityRequest.class)
                .doOnNext(dto -> ValidationUtil.validate(dto, validator))
                .flatMap(cityService::createCity)
                .flatMap(createdData -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createdData))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> updateCity(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UpdateCityRequest.class)
                .doOnNext(dto -> ValidationUtil.validate(dto, validator))
                .flatMap(cityService::updateCity)
                .flatMap(updatedEntity -> ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(updatedEntity))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCity(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id"));
        return cityService.deleteCityById(id)
                .then(ServerResponse
                                .ok()
                                .contentType(MediaType.TEXT_PLAIN)
                                .bodyValue("Deleted city with id: " + id))
                        .switchIfEmpty(ServerResponse.notFound().build());
    }
}
