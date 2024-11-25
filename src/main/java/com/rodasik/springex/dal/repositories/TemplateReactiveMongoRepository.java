package com.rodasik.springex.dal.repositories;

import com.rodasik.springex.dal.entities.BaseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface TemplateReactiveMongoRepository<T extends BaseEntity> extends ReactiveMongoRepository<T, UUID> {
}
