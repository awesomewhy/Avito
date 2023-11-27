package com.example.avito.controller.user.profile;

import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.UpdateProfileDto;
import com.example.avito.dto.userdto.UpdateUserEmailDto;
import com.example.avito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/changeemail")
    public ResponseEntity<?> changeEmail(@RequestBody UpdateUserEmailDto updateUserEmailDto) {
        return userService.updateUserEmail(updateUserEmailDto);
    }

    @PostMapping("/deleteprofile")
    public ResponseEntity<?> deleteProfile(@RequestBody DeleteProfileDto deleteProfileDto) {
        return userService.deleteProfile(deleteProfileDto);
    }

}
