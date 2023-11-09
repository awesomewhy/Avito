package com.example.avito.controllers;

import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    public final ProductService productService;
    public final AdminService adminService;

    @GetMapping("/sortbycity")
    public List<Product> sortByCity() {
        return adminService.sortProductsByCity();
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/getme")
    public Optional<User> getMyProfile() {
        return userService.getMyProfile();
    }

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "unsecured data";
    }

    @GetMapping("/login")
    public String login() {
        return "login data";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "secured data";
    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
