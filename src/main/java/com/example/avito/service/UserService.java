package com.example.avito.service;

import com.example.avito.dto.userdto.ChangePasswordDto;
import com.example.avito.dto.userdto.DeleteProfileDto;
import com.example.avito.dto.userdto.RegistrationUserDto;
import com.example.avito.dto.userdto.UpdateProfileDto;
import com.example.avito.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> getAuthenticationPrincipalUserByEmail();
    User createNewUser(@RequestBody RegistrationUserDto registrationUserDto);
    ResponseEntity<?> updateUser(@RequestBody UpdateProfileDto updateUserDto);
    ResponseEntity<?> getMyProfile();
    ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto);
    ResponseEntity<?> deleteProfile(@RequestBody DeleteProfileDto deleteProfileDto);
}
