package com.example.avito.service.impl;

import com.example.avito.dtos.RegisterRequest;
import com.example.avito.repository.UserRepository;
import com.example.avito.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(RegisterRequest signupRequest) {
        return false;
    }
}
