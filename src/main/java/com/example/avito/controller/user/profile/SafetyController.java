package com.example.avito.controller.user.profile;

import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.UpdateProfileDto;
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
    public ResponseEntity<?> updateUser(@RequestBody UpdateProfileDto updateUserDto) {
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
