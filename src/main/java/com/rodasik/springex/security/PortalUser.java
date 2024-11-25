package com.rodasik.springex.security;

import com.rodasik.springex.dal.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class PortalUser extends User implements UserDetails {
    private static final String ROLE_PREFIX = "ROLE_";
    PortalUser(User user) { super(user); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles()
                .stream()
                .map(Enum::name)
                .map(String::toUpperCase)
                .map(role -> ROLE_PREFIX + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return "-"; }

    @Override
    public String getUsername() { return getEmail() + ' ' + getUsername(); };

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
