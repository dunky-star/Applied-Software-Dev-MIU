package edu.miu.cs.cs489appsd.hotel.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
public class MyTestController {
    // This is a test controller to check if the application is running
    @RequestMapping
    public ResponseEntity<String> getList() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(List.of(
                "My",
                "Nice",
                "Hotel destination"
        ).toString());
    }
}
