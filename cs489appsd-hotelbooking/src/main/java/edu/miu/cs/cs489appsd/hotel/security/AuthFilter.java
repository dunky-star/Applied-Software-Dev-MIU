package edu.miu.cs.cs489appsd.hotel.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthFilter implements WebFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = getTokenFromRequest(exchange);

        if (token != null) {
            try {
                String email = jwtUtils.getUsernameFromToken(token);
//                String role = jwtUtils.getRoleFromToken(token);

                return customUserDetailsService.findByUsername(email)
                        .filter(userDetails -> jwtUtils.isTokenValid(token, userDetails))
                        .flatMap(userDetails -> {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                            return chain.filter(exchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                        })
                        .switchIfEmpty(chain.filter(exchange));
            } catch (Exception e) {
                log.error("Error validating JWT token: {}", e.getMessage());
            }
        }

        return chain.filter(exchange);
    }


    private String getTokenFromRequest(ServerWebExchange exchange) {
        String bearer = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}

