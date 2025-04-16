package edu.cs489.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class MyController {
    @GetMapping
    public String home() {
        return "Spring security home page";
    }
}
