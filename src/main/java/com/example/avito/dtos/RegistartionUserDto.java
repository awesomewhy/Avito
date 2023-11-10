package com.example.avito.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
public class RegistartionUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String city;
}
