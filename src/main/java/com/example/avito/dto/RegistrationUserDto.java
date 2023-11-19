package com.example.avito.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class RegistrationUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String city;
}
