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
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping
    public ResponseEntity<Response> updateOwnAccount(@RequestBody UserDto userDto) {
        Response response = userService.updateOwnAccount(userDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping()
    public ResponseEntity<Response> deleteOwnAccount() {
        Response response = userService.deleteOwnAccount();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/me")
    public ResponseEntity<Response> getOwnAccountDetails() {
        Response response = userService.getOwnAccountDetails();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/me/bookings")
    public ResponseEntity<Response> getMyBookingHistory() {
        Response response = userService.getMyBookingHistory();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
