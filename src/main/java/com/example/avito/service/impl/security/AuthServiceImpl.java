package com.example.avito.service.impl.security;

import com.example.avito.dto.jwtdto.JwtRequestDto;
import com.example.avito.dto.jwtdto.JwtResponseDto;
import com.example.avito.dto.userdto.RegisterRequestDto;
import com.example.avito.dto.userdto.RegistrationUserDto;
import com.example.avito.dto.userdto.UserDto;
import com.example.avito.entity.User;
import com.example.avito.exception.ErrorResponse;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.AuthService;
import com.example.avito.service.UserService;
import com.example.avito.util.JwtTokenUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final static String USER_WHIT_THIS_EMAIL_EXIST = "user with email уже существует";
    private final static String PASSWORDS_DID_NOT_MATCH = "пароли не совпали";
    private final static String INCORRECT_LOGIN_OR_PASSWORD = "неправильный логин или пароль";
    private final static String USER_REGISTER = "пользователь зарегестрирован";
    private final static String INVALID_EMAIL = "Неверно введенная почта";
    private final static String INVALID_PASSWORD = "Пароль введет с недопустимыми символами или пароль скишком маленький";


    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequest) {
        if (StringUtils.isEmpty(authRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Email is required"));
        }

        if (StringUtils.isEmpty(authRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Password is required"));
        }
        try {
            authenticateUser(authRequest.getEmail(), authRequest.getPassword());
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
            String token = jwtTokenUtils.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponseDto(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), INCORRECT_LOGIN_OR_PASSWORD));
        }
    }

    @Override
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), PASSWORDS_DID_NOT_MATCH), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findByEmail(registrationUserDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), USER_WHIT_THIS_EMAIL_EXIST));
        }
//        if(!Validation.isValidEmailAddress(registrationUserDto.getEmail())) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), INVALID_EMAIL), HttpStatus.BAD_REQUEST);
//        }
//        if(!Validation.isValidPassword(registrationUserDto.getPassword())) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), INVALID_PASSWORD), HttpStatus.BAD_REQUEST);
//        }
        userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok().body(USER_REGISTER);
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
