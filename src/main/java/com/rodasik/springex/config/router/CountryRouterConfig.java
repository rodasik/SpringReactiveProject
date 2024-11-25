package com.rodasik.springex.config.router;

import com.rodasik.springex.api.handlers.CountryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CountryRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> countryRouterFunction(CountryHandler countryHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/public/countries")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::getCountries)
                .andRoute(RequestPredicates.POST("/public/countries/search")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::searchCountries)
                .andRoute(RequestPredicates.DELETE("/admin/countries/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::deleteCountry)
                .andRoute(RequestPredicates.POST("/admin/countries")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::createCountry)
                .andRoute(RequestPredicates.PUT("/admin/countries")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::updateCountry);
    }
}
