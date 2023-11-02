package com.example.avito.controllers;

import com.example.avito.service.AdminService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


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
