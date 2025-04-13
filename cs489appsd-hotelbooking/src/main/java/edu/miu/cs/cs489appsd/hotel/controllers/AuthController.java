package edu.miu.cs.cs489appsd.hotel.controllers;

import edu.miu.cs.cs489appsd.hotel.dtos.LoginRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.RegistrationRequest;
import edu.miu.cs.cs489appsd.hotel.dtos.Response;
import edu.miu.cs.cs489appsd.hotel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AuthController {

    private final UserService userService;

    @PostMapping() // POST /api/v1/users
    public ResponseEntity<Response> registerUser(@Valid @RequestBody RegistrationRequest request) {
        Response response = userService.registerUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/sessions") // POST /api/v1/users/sessions
    public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginRequest request) {
        Response response = userService.loginUser(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
