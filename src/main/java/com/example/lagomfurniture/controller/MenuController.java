package com.example.lagomfurniture.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("")
    public String home() {
        return "index";
    }
}
