package com.example.avito.service;

import com.example.avito.dtos.JwtRequestDto;
import com.example.avito.dtos.RegistartionUserDto;
import com.example.avito.dtos.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    boolean createUser(RegisterRequestDto signupRequest);
    ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest);
    ResponseEntity<?> createNewUser(@RequestBody RegistartionUserDto registartionUserDto);

}
