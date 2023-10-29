package com.example.avito.dtos;

import lombok.Data;


@Data
public class RegistartionUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
