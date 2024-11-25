package com.rodasik.springex.config.router;

import com.rodasik.springex.api.handlers.CityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> cityRouterFunction(CityHandler cityHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/public/cities/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), cityHandler::getCitiesByCountryId)
                .andRoute(RequestPredicates.POST("/public/cities")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), cityHandler::getCities)
                .andRoute(RequestPredicates.POST("/public/cities/search")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), cityHandler::searchCities)
                .andRoute(RequestPredicates.DELETE("/admin/cities/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), cityHandler::deleteCity)
                .andRoute(RequestPredicates.POST("/admin/cities")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), cityHandler::createCity)
                .andRoute(RequestPredicates.PUT("/admin/cities")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), cityHandler::updateCity);
    }
}
