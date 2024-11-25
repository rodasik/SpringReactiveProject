package com.rodasik.springex.bll;

import com.rodasik.springex.api.mappers.CountryMapper;
import com.rodasik.springex.api.requests.CreateCountryRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateCountryRequest;
import com.rodasik.springex.dal.entities.Country;
import com.rodasik.springex.dal.entities.User;
import com.rodasik.springex.dal.repositories.CountryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService, FinderService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Country> getCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Mono<Country> getCountryById(final UUID id) {
        return countryRepository.findById(id);
    }

    @Override
    public Mono<Page<Country>> searchCountries(SearchRequest request) {
        Pageable pageable = preparePageable(request);
        if (nonNull(request.getFilter()) && !request.getFilter().isEmpty()) {
            return countryRepository
                    .countByNameContainsOrCodeContains(request.getFilter(), request.getFilter())
                    .flatMap(count -> countryRepository.getPaginatedCountriesWithFilter(request.getFilter(), pageable)
                            .collectList()
                            .map(elems -> new PageImpl<>(elems, pageable, count)));
        }
        return countryRepository
                .count()
                .flatMap(count -> countryRepository.getPaginatedCountries(pageable)
                        .collectList()
                        .map(elems -> new PageImpl<>(elems, pageable, count)));
    }

    @Override
    public Mono<Country> createCountry(CreateCountryRequest request) {
        var mappedRequest = countryMapper.createRequestToEntity(request);
        return countryRepository.save(mappedRequest);
    }

    @Override
    public Mono<Country> updateCountry(UpdateCountryRequest request) {
        return countryRepository
        .findById(request.getId())
        .flatMap(entity -> {
            entity.setName(request.getName());
            entity.setCode(request.getCode());
            return countryRepository.save(entity);
        })
        .switchIfEmpty(countryRepository.save(countryMapper.updateRequestToEntity(request)));
        // Example with MongoReactiveTemplate
        /*Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update()
                .set("name", request.getName())
                .set("code", request.getCode());
        return mongoTemplate.findAndModify(query, update, Country.class);*/
    }

    @Override
    public Mono<Void> deleteCountryById(final UUID id) {
        return countryRepository
                .findById(id)
                        .flatMap(countryRepository::delete);
    }
}
