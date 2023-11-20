package com.example.avito.service;

import com.example.avito.dto.jwtdto.JwtRequestDto;
import com.example.avito.dto.userdto.RegistrationUserDto;
import com.example.avito.dto.userdto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    boolean createUser(RegisterRequestDto signupRequest);
    ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest);
    ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registartionUserDto);

}
