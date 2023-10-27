package com.example.avito.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class UserLogin {
    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "registration";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin";
    }
}
