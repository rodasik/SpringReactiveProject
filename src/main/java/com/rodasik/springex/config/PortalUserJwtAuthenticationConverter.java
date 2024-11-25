package com.rodasik.springex.config;

import com.rodasik.springex.security.PortalReactiveUserDetailsService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class PortalUserJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private static final String GROUP_CLAIMS = "group";
    private static final String ROLE_PREFIX = "ROLE_";

    private final PortalReactiveUserDetailsService portalUserDetailsService;

    public PortalUserJwtAuthenticationConverter(PortalReactiveUserDetailsService portalUserDetailsService) {
        this.portalUserDetailsService = portalUserDetailsService;
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = decodeAuthorities(jwt);
        return portalUserDetailsService
                .findByUsername(jwt.getClaimAsString("email"))
                .map(user -> new UsernamePasswordAuthenticationToken(user, "-", authorities));
    }

    private Collection<GrantedAuthority> decodeAuthorities(Jwt jwt) {
        return this
                .getScopes(jwt)
                .stream()
                .map(auth -> ROLE_PREFIX + auth.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Collection<String> getScopes(Jwt jwt) {
        Object scopes = jwt.getClaims().get(GROUP_CLAIMS);
        if (scopes instanceof Collection) {
            return (Collection<String>) scopes;
        }
        return Collections.emptyList();
    }
}
