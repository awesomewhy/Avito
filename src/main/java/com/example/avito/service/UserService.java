package com.example.avito.service;

import com.example.avito.dtos.RegistrationUserDto;
import com.example.avito.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    User createNewUser(RegistrationUserDto registartionUserDto);
    User updateUser(@RequestBody User user);
    Optional<User> getMyProfile();
}
