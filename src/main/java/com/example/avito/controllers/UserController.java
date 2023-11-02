package com.example.avito.controllers;

import com.example.avito.dtos.ProductDto;
import com.example.avito.entity.Product;
import com.example.avito.service.AddItem;
import com.example.avito.service.AdminService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    public final AddItem addItem;
    public final AdminService adminService;

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
    public Product sale(@RequestBody ProductDto productDto) {
        return addItem.addItem(productDto);
    }
}
