package com.rodasik.springex.filter;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class LogFilter implements WebFilter {
    private static final Logger logger = LogManager.getLogger(LogFilter.class);

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.info("Requested {}", exchange.getRequest().getPath().value());
        return chain.filter(exchange);
    }
}
