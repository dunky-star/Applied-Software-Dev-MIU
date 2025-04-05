package edu.miu.cs489.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomePageController {
    @GetMapping(value = {"/", "/home", "/elibrary"})
    public String HomePage() {
        return "home/index";
    }
}
