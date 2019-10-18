package com.flysafe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String greeting() {
        return "redirect:/swagger-ui.html";
    }
}
