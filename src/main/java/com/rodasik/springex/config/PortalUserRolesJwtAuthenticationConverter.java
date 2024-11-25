package com.rodasik.springex.config;

import com.rodasik.springex.security.PortalReactiveUserDetailsService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

public class PortalUserRolesJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private final PortalReactiveUserDetailsService userDetailsService;

    public PortalUserRolesJwtAuthenticationConverter(PortalReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        return userDetailsService
                .findByUsername(jwt.getClaimAsString("email"))
                .map(user -> new UsernamePasswordAuthenticationToken(user, "-", user.getAuthorities()));
    }
}
