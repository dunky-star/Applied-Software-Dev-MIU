package edu.miu.cs.cs489appsd.hotel.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements WebFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTH_ERROR_MSG = "Authentication failed - {}";

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return extractToken(exchange)
                .flatMap(token -> authenticate(token, exchange, chain))
                .onErrorResume(e -> {
                    log.error(AUTH_ERROR_MSG, e.getMessage());
                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    private Mono<String> extractToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(BEARER_PREFIX))
                .map(header -> header.substring(BEARER_PREFIX.length()));
    }

    private Mono<Void> authenticate(String token,
                                    ServerWebExchange exchange,
                                    WebFilterChain chain) {
        return validateToken(token)
                .flatMap(this::loadUserDetails)
                .filter(userDetails -> jwtUtils.isTokenValid(token, userDetails))
                .flatMap(userDetails -> establishSecurityContext(userDetails, exchange, chain));
    }

    private Mono<String> validateToken(String token) {
        return Mono.fromCallable(() -> jwtUtils.getUsernameFromToken(token))
                .doOnSuccess(username -> log.debug("Token validated for: {}", username))
                .doOnError(e -> log.warn("Token validation failed", e));
    }

    private Mono<UserDetails> loadUserDetails(String username) {
        return userDetailsService.findByUsername(username)
                .doOnNext(user -> log.debug("User loaded: {}", user.getUsername()))
                .doOnError(e -> log.warn("User loading failed", e));
    }

    private Mono<Void> establishSecurityContext(UserDetails userDetails,
                                                ServerWebExchange exchange,
                                                WebFilterChain chain) {
        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        log.debug("Authenticating {} with roles: {}",
                userDetails.getUsername(),
                userDetails.getAuthorities());

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
    }
}