package com.rodasik.springex.bll;

import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.common.Role;
import com.rodasik.springex.dal.entities.User;
import com.rodasik.springex.dal.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, FinderService {
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public Mono<User> getUserByEmail(String email) { return userRepository.findByEmail(email); }

    @Override
    @PreAuthorize("isAnonymous() or isAuthenticated()")
    public Mono<User> getUserByUsername(String username) { return userRepository.findByUsername(username); }

    @Override
    public Mono<User> createUser(User user) {
        // for now creates only USER
        user.setRoles(List.of(Role.USER));
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return userRepository
                .findById(Objects.requireNonNull(user.getId()))
                .flatMap(entity -> {
                    entity.setUsername(user.getUsername());
                    entity.setEmail(user.getEmail());
                    entity.setLastName(user.getLastName());
                    entity.setFirstName(user.getFirstName());
                    return userRepository.save(entity);
                })
                .switchIfEmpty(createUser(user));
    }

    @Override
    public Mono<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAllUsers() { return userRepository.findAll(); }

    @Override
    public Flux<User> searchUsers(SearchRequest request) {
        return userRepository.getUsersPaginated(preparePageable(request));
    }

    public Mono<Void> deleteUserById(UUID id) {
        return userRepository.findById(id)
                .flatMap(userRepository::delete);
    }
}
