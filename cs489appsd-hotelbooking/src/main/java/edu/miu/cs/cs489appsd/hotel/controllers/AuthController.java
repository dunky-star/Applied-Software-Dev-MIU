package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.LoginRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.RegistrationRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<Response>> registerUser(@Valid @RequestBody Mono<RegistrationRequest> requestMono) {
        return requestMono
                .flatMap(userService::registerUser)
                .map(r -> ResponseEntity.status(r.getStatus()).body(r)); // use 'r' instead of 'response'
    }

    @PostMapping("/sessions")
    public Mono<ResponseEntity<Response>> loginUser(@Valid @RequestBody Mono<LoginRequest> requestMono) {
        return requestMono
                .flatMap(userService::loginUser)
                .map(r -> ResponseEntity.status(r.getStatus()).body(r)); // use 'r' instead of 'response'
    }

}

