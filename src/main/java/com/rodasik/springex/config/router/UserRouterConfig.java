package com.rodasik.springex.config.router;

import com.rodasik.springex.api.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/public/users")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getAllUsers)
                .andRoute(RequestPredicates.POST("/public/users")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::searchUsers)
                .andRoute(RequestPredicates.GET("/admin/users/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
                .andRoute(RequestPredicates.POST("/admin/users")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                .andRoute(RequestPredicates.PUT("/admin/users")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(RequestPredicates.DELETE("/admin/users/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
    }
}