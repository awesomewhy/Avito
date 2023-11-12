package com.example.avito.service;

import com.example.avito.dtos.ChangePasswordDto;
import com.example.avito.dtos.DeleteProfileDto;
import com.example.avito.dtos.RegistrationUserDto;
import com.example.avito.dtos.UpdateUserDto;
import com.example.avito.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    User createNewUser(@RequestBody RegistrationUserDto registrationUserDto);
    ResponseEntity<?> updateUser(@RequestBody UpdateUserDto updateUserDto);
    Optional<User> getMyProfile();
    ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto);
    ResponseEntity<?> deleteProfile(@RequestBody DeleteProfileDto deleteProfileDto);
}
