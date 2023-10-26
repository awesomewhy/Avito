package com.example.avito.service;

import com.example.avito.dtos.JwtRequest;
import com.example.avito.dtos.RegistartionUserDto;
import com.example.avito.dtos.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    boolean createUser(RegisterRequest signupRequest);
    ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest);
    ResponseEntity<?> createNewUser(@RequestBody RegistartionUserDto registartionUserDto);

}
