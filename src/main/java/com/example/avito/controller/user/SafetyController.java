package com.example.avito.controller.user;

import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.UpdateUserDto;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/safety")
@RequiredArgsConstructor
public class SafetyController {
    private final UserService userService;
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
}
