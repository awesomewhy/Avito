package com.example.avito.service;

import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.RegistrationUserDto;
import com.example.avito.dto.userdto.UpdateUserDto;
import com.example.avito.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService extends UserDetailsService {
    User createNewUser(@RequestBody RegistrationUserDto registrationUserDto);
    ResponseEntity<?> updateUser(@AuthenticationPrincipal String email, @RequestBody UpdateUserDto updateUserDto);
    ResponseEntity<?> getMyProfile(@AuthenticationPrincipal String email);
    ResponseEntity<?> changePassword(@AuthenticationPrincipal String email, @RequestBody ChangePasswordDto changePasswordDto);
    ResponseEntity<?> deleteProfile(@AuthenticationPrincipal String email, @RequestBody DeleteProfileDto deleteProfileDto);
}
