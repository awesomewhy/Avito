package com.example.avito.service;

import com.example.avito.dtos.RegisterRequest;

public interface AuthService {
    boolean createUser(RegisterRequest signupRequest);
}
