package com.example.avito.controllers;

import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.example.avito.service.SaleService;
import com.example.avito.service.UserService;
import com.example.avito.service.impl.AdminServiceImpl;
import com.example.avito.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
//    public final SaleService saleService;
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

//    @PostMapping("/sale")
//    public String sale() {
//        return saleService.save();
//    }
}
