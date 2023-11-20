package com.example.avito.controller;

import com.example.avito.dto.*;
import com.example.avito.service.ProductService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal String email) {
        return userService.getMyProfile(email);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String email, @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(email, updateUserDto);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal String email, @RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(email, changePasswordDto);
    }

    @PostMapping("/deleteprofile")
    public ResponseEntity<?> deleteProfile(@AuthenticationPrincipal String email, @RequestBody DeleteProfileDto deleteProfileDto) {
        return userService.deleteProfile(email, deleteProfileDto);
    }

    @GetMapping("/sortbycity")
    public List<ProductShowDto> sortByCity() {
        return productService.sortProductsByCity();
    }

    @GetMapping("/sortbyprice")
    public List<ProductShowDto> sortByPrice(@RequestBody PriceSortDto priceSortDto) throws Exception {
        return productService.sortByPrice(priceSortDto);
    }

}
