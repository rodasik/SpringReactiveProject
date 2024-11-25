package com.rodasik.springex.config;

import com.rodasik.springex.security.PortalReactiveUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

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
                .cors(ServerHttpSecurity.CorsSpec::disable)
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
        configuration.addAllowedOriginPattern(CorsConfiguration.ALL);
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
