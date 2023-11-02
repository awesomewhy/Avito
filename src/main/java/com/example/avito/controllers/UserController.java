package com.example.avito.controllers;

import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import com.example.avito.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    public final ProductService productService;
    public final AdminService adminService;
    private final JwtTokenUtils jwtTokenUtils;

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

    @PostMapping("/additem")
    public Product addProduct(@RequestBody ProductDto productDto) {
        return productService.addItem(productDto);
    }

    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/products")
    public String updateUser() {
        List<Product> products = productService.getAllProducts();
        return adminService.convertObjectsToJson(products);
    }
}
