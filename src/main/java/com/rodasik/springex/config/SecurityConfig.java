package com.rodasik.springex.config;

import com.rodasik.springex.security.PortalReactiveUserDetailsService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {
    private final PortalReactiveUserDetailsService userDetailsService;

    public SecurityConfig(PortalReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .pathMatchers("/public/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtSpec -> jwtSpec
                                .jwtAuthenticationConverter(portalUserJwtAuthenticationConverter())
                                .jwtAuthenticationConverter(portalUserRolesJwtAuthenticationConverter())
                        )
                )
                .addFilterBefore(new PublicPathBypassFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    public PortalUserJwtAuthenticationConverter portalUserJwtAuthenticationConverter() {
        return new PortalUserJwtAuthenticationConverter(userDetailsService);
    }

    @Bean
    public PortalUserRolesJwtAuthenticationConverter portalUserRolesJwtAuthenticationConverter() {
        return new PortalUserRolesJwtAuthenticationConverter(userDetailsService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static class PublicPathBypassFilter implements WebFilter {
        @Override
        @NonNull
        public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
            String path = exchange.getRequest().getPath().value();
            if (path.startsWith("/public/")) {
                // Remove the Authorization header for /public/** paths
                exchange.getRequest().mutate().headers(headers -> headers.remove("Authorization"));
            }
            // Proceed with the filter chain
            return chain.filter(exchange);
        }
    }
}
