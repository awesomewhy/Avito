package com.example.avito.service;

import com.example.avito.dtos.RegistartionUserDto;
import com.example.avito.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    User createNewUser(RegistartionUserDto registartionUserDto);
    List<User> getAllPersons();
    User getUserIdByEmail(String email);
    User updateUser(User user);
    User getMyProfile();

}
