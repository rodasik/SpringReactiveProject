package com.rodasik.springex.dal.repositories;

import com.rodasik.springex.dal.entities.BaseEntity;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.Objects.isNull;

public class TemplateReactiveMongoRepositoryImpl<T extends BaseEntity> extends SimpleReactiveMongoRepository<T, UUID> implements TemplateReactiveMongoRepository<T> {
    protected MongoEntityInformation<T, UUID> entityInformation;
    protected ReactiveMongoOperations operations;

    TemplateReactiveMongoRepositoryImpl(MongoEntityInformation<T, UUID> entityInformation, ReactiveMongoTemplate template) {
        super(entityInformation, template);
        this.entityInformation = entityInformation;
        this.operations = template;
    }

    @Override
    @NonNull
    public <S extends T> Mono<S> insert(S entity) {
        if (isNull(entity.getId())) {
            generateId(entity);
        }
        return super.insert(entity);
    }

    @Override
    @NonNull
    public <S extends T> Flux<S> insert(Iterable<S> entities) {
        for (S entity : entities) {
            if (isNull(entity.getId())) {
                generateId(entity);
            }
        }
        return super.insert(entities);
    }

    @Override
    @NonNull
    public <S extends T> Mono<S> save(@NonNull S entity) {
        Assert.notNull(entity, "Entity must not be null");
        if (isNull(entity.getId())) {
            generateId(entity);
        }
        return operations.save(entity);
    }

    @Override
    @NonNull
    public <S extends T> Flux<S> saveAll(@NonNull Iterable<S> entities) {
        for (S entity : entities) {
            if (isNull(entity.getId())) {
                generateId(entity);
            }
        }
        return Flux.fromIterable(entities).flatMap(operations::save);
    }

    protected <S extends T> void generateId(S entity) { entity.setId(UUID.randomUUID()); }
}
