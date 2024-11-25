package com.rodasik.springex.bll;

import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.dal.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<User> getUserByUsername(String username);
    Mono<User> getUserByEmail(String email);
    Mono<User> createUser(User user);
    Mono<User> updateUser(User user);
    Mono<User> getUserById(UUID id);
    Flux<User> getAllUsers();
    Flux<User> searchUsers(SearchRequest requestDto);
    Mono<Void> deleteUserById(UUID id);
}
