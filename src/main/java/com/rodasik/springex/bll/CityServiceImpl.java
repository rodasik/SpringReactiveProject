package com.rodasik.springex.bll;

import com.rodasik.springex.api.mappers.CityMapper;
import com.rodasik.springex.api.requests.CreateCityRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateCityRequest;
import com.rodasik.springex.dal.entities.City;
import com.rodasik.springex.dal.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService, FinderService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public Flux<City> getCitiesByCountryId(UUID countryId)
    {
        return cityRepository.getPaginatedCitiesWithCountryId(countryId, Pageable.unpaged());
    }

    @Override
    @SneakyThrows
    public Flux<City> getCities(SearchRequest query) {
        if (nonNull(query.getFilter()) && !query.getFilter().isEmpty()) {
            return cityRepository.getPaginatedCitiesWithFilterAndCountryId(query.getId(), query.getFilter(), preparePageable(query));
        }
        return cityRepository.getPaginatedCitiesWithCountryId(query.getId(), preparePageable(query));
    }

    @Override
    public Mono<Page<City>> searchCities(SearchRequest query) {
        Pageable pageable = preparePageable(query);
        if (nonNull(query.getFilter()) && !query.getFilter().isEmpty()) {
            return cityRepository.countByNameContainsOrCodeContainsOrDescriptionContains(query.getFilter(), query.getFilter(), query.getFilter())
                    .flatMap(count ->
                            cityRepository.getPaginatedCitiesWithFilter(query.getFilter(), pageable)
                                    .collectList()
                                    .map(elements -> new PageImpl<>(elements, pageable, count)));
        }
        return cityRepository.count()
                .flatMap(count ->
                        cityRepository.getPaginatedCities(pageable)
                                .collectList()
                                .map(elements -> new PageImpl<>(elements, pageable, count)));
    }

    @Override
    public Mono<City> createCity(CreateCityRequest request) {
        var mappedRequest = cityMapper.createRequestToEntity(request);
        return cityRepository.save(mappedRequest);
    }

    @Override
    public Mono<City> updateCity(UpdateCityRequest request) {
        return cityRepository
            .findById(request.getId())
            .flatMap(entity -> {
                entity.setPopulation(request.getPopulation());
                entity.setSurfaceArea(request.getSurfaceArea());
                entity.setCode(request.getCode());
                entity.setParentId(request.getParentId());
                entity.setDescription(request.getDescription());
                entity.setName(request.getName());
                return cityRepository.save(entity);
            })
            .switchIfEmpty(cityRepository.save(cityMapper.updateRequestToEntity(request)));
    }

    @Override
    public Mono<Void> deleteCityById(UUID id) {
        return cityRepository
                .findById(id)
                .flatMap(cityRepository::delete);
    }
}
