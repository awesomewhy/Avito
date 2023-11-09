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
    @GetMapping("/users")
    public String getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return adminService.convertObjectsToJson(users);
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin data";
    }
}
