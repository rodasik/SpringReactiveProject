package com.rodasik.springex.dal.repositories;

import com.rodasik.springex.dal.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, UUID> {
    Mono<User> findByEmail(String email);

    Mono<User> findByUsername(String username);

    @Query("{ id: { $exists: true }}")
    Flux<User> getUsersPaginated(final Pageable pageable);
}
