package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.UserDto;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Response>> getAllUsers() {
        return userService.getAllUsers()
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @PutMapping
    public Mono<ResponseEntity<Response>> updateOwnAccount(@RequestBody UserDto userDto) {
        return userService.updateOwnAccount(userDto)
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Response>> deleteOwnAccount() {
        return userService.deleteOwnAccount()
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<Response>> getOwnAccountDetails() {
        return userService.getOwnAccountDetails()
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }

    @GetMapping("/me/bookings")
    public Mono<ResponseEntity<Response>> getMyBookingHistory() {
        return userService.getMyBookingHistory()
                .map(response -> ResponseEntity.status(response.getStatus()).body(response));
    }
}
