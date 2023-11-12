package com.example.avito.service.impl;

import com.example.avito.dtos.*;
import com.example.avito.entity.User;
import com.example.avito.exceptions.AppError;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.AuthService;
import com.example.avito.service.UserService;
import com.example.avito.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public boolean createUser(RegisterRequestDto signupRequest) {
        return false;
    }

    @Override
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(token));
    }

    @Override
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпали"), HttpStatus.UNAUTHORIZED);
        }
        if (userRepository.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным email уже существует"), HttpStatus.BAD_REQUEST);
        }
//        if(!EmailValidation.isValidEmailAddress(registartionUserDto.getEmail())) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Неверно введенная почта"), HttpStatus.BAD_REQUEST);
//        }
//        if(!PasswordValidation.isValidPassword(registartionUserDto.getPassword())) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароль введет с недопустимыми символами или пароль скишком маленький"), HttpStatus.BAD_REQUEST);
//        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }
}
