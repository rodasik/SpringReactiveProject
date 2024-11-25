package com.rodasik.springex.security;

import com.rodasik.springex.bll.UserService;
import com.rodasik.springex.dal.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PortalReactiveUserDetailsService implements ReactiveUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(PortalReactiveUserDetailsService.class);
    private final UserService userService;

    public PortalReactiveUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        logger.info("Looking for user by username: {}", username);
        return userService.getUserByUsername(username).switchIfEmpty(findByEmail(username)).map(PortalUser::new);
    }

    public Mono<User> findByEmail(String email) {
        logger.info("Looking for user by email: {}", email);
        return userService.getUserByEmail(email).switchIfEmpty(Mono.empty());
    }
}
