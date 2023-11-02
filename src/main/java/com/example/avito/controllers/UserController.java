package com.example.avito.controllers;

import com.example.avito.entity.User;
import com.example.avito.service.UserService;
import com.example.avito.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserServiceImpl userService;

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "unsecured data ";
    }

    @GetMapping("/login")
    public String login() {
        return "login data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "secured data";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin data";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/getallusers")
    public String getAllUsers() {
        List<User> users = userService.getAllPersons();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(users);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Error converting users to JSON";
        }
    }

}
