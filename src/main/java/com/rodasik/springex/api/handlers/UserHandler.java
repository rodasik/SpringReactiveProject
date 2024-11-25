package com.rodasik.springex.api.handlers;

import com.rodasik.springex.api.dto.UserDTO;
import com.rodasik.springex.api.mappers.UserMapperStruct;
import com.rodasik.springex.api.requests.CreateUserRequest;
import com.rodasik.springex.api.requests.SearchRequest;
import com.rodasik.springex.api.requests.UpdateUserRequest;
import com.rodasik.springex.bll.UserService;
import com.rodasik.springex.common.ValidationUtil;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;
    private final UserMapperStruct userMapper;
    private final Validator validator;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getAllUsers().map(userMapper::toDTO), UserDTO.class);
    }

    public Mono<ServerResponse> searchUsers(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(SearchRequest.class)
                .flatMap(request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.searchUsers(request).map(userMapper::toDTO), UserDTO.class));
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return userService
                .getUserById(UUID.fromString(request.pathVariable("id")))
                .map(userMapper::toDTO)
                .flatMap(it -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(it)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request
                .bodyToMono(CreateUserRequest.class)
                .doOnNext(dto -> ValidationUtil.validate(dto, validator))
                .flatMap(createData ->
                        userService.createUser(userMapper.createRequestToEntity(createData)))
                .flatMap(createdEntity -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userMapper.toDTO(createdEntity)));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(UpdateUserRequest.class)
                .doOnNext(dto -> ValidationUtil.validate(dto, validator))
                .flatMap(updatedData -> userService
                        .updateUser(userMapper.updateRequestToEntity(updatedData))
                .flatMap(updatedEntity -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userMapper.toDTO(updatedEntity)))
                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        UUID userId = UUID.fromString(request.pathVariable("id"));
        return userService.deleteUserById(userId)
                .then(ServerResponse
                        .ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue("Deleted country with id: " + userId))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
