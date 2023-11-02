package com.example.avito.controllers;

import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    @PostMapping("/users")
    public String getAllUsers() {
        List<User> users = userService.getAllPersons();
        return adminService.convertObjectsToJson(users);
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin data";
    }
}
