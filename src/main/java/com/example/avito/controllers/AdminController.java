package com.example.avito.controllers;

import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/users")
    public String getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return adminService.convertObjectsToJson(users);
    }
    @PostMapping("/setadminrole/{id}")
    public ResponseEntity<?> setAdminRole(@PathVariable Long id) {
        return adminService.setAdminRole(id);
    }

    @GetMapping("/admin")
    public String adminData() {
        return "admin data";
    }
}
