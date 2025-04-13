package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.dtos.UserDto;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity<Response> updateOwnAccount(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateOwnAccount(userDto));
    }

    @DeleteMapping()
    public ResponseEntity<Response> deleteOwnAccount() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteOwnAccount());
    }
    @GetMapping("/me")
    public ResponseEntity<Response> getOwnAccountDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOwnAccountDetails());
    }
    @GetMapping("/me/bookings")
    public ResponseEntity<Response> getMyBookingHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyBookingHistory());
    }
}
