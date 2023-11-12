package com.example.avito.controllers;

import com.example.avito.dtos.ChangePasswordDto;
import com.example.avito.dtos.DeleteProfileDto;
import com.example.avito.dtos.UpdateUserDto;
import com.example.avito.entity.Product;
import com.example.avito.entity.User;
import com.example.avito.service.AdminService;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AdminService adminService;

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

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(changePasswordDto);
    }
    @PostMapping("/deleteprofile")
    public ResponseEntity<?> deleteProfile(@RequestBody DeleteProfileDto deleteProfileDto) {
        return userService.deleteProfile(deleteProfileDto);
    }

}
