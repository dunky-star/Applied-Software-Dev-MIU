package edu.miu.cs.cs489appsd.hotel.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomAccessDenialHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException accessDeniedException) {
        return Mono.defer(() -> {
            try {
                // Build the custom error response
                Response errorResponse = Response.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .message(accessDeniedException.getMessage())
                        .build();

                // Serialize response to JSON bytes
                byte[] responseBytes = objectMapper.writeValueAsBytes(errorResponse);

                // Set response metadata
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                // Write the JSON to response body
                return exchange.getResponse()
                        .writeWith(Mono.just(exchange.getResponse()
                                .bufferFactory()
                                .wrap(responseBytes)));
            } catch (Exception e) {
                return Mono.error(e);
            }
        });
    }
}
