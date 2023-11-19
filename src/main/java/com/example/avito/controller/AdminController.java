package com.example.avito.controller;

import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/adminrole/{id}")
    public ResponseEntity<?> setAdminRole(@PathVariable Long id) {
        return adminService.setAdminRole(id);
    }

    @PostMapping("/userrole/{id}")
    public ResponseEntity<?> setUserRole(@PathVariable Long id) {
        return adminService.setUserRole(id);
    }
}