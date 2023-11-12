package com.example.avito.service;

import com.example.avito.dtos.*;
import com.example.avito.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User createNewUser(@RequestBody RegistrationUserDto registrationUserDto);
    ResponseEntity<?> updateUser(@AuthenticationPrincipal String email, @RequestBody UpdateUserDto updateUserDto);
    Optional<MyProfileDto> getMyProfile(@AuthenticationPrincipal String email);
    ResponseEntity<?> changePassword(@AuthenticationPrincipal String email, @RequestBody ChangePasswordDto changePasswordDto);
    ResponseEntity<?> deleteProfile(@AuthenticationPrincipal String email, @RequestBody DeleteProfileDto deleteProfileDto);
}
