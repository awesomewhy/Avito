package com.example.avito.service.impl;

import com.example.avito.Validation.EmailValidation;
import com.example.avito.Validation.PasswordValidation;
import com.example.avito.dtos.*;
import com.example.avito.entity.User;
import com.example.avito.exceptions.AppError;
import com.example.avito.service.AuthService;
import com.example.avito.service.UserService;
import com.example.avito.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Email;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public boolean createUser(RegisterRequest signupRequest) {
        return false;
    }

    @Override
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public ResponseEntity<?> createNewUser(@RequestBody RegistartionUserDto registartionUserDto) {
        if (!registartionUserDto.getPassword().equals(registartionUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпали"), HttpStatus.UNAUTHORIZED);
        }
        if (userService.findByUsername(registartionUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        if(!EmailValidation.isValidEmail(registartionUserDto.getEmail())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Неверно введенная почта"), HttpStatus.BAD_REQUEST);
        }
        if(!PasswordValidation.isvalidPassword(registartionUserDto.getPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароль введет с недопустимыми символами или пароль скишком маленький"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registartionUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
