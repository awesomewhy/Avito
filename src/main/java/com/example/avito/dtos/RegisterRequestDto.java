package com.example.avito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDto {
    private String email;
    private String name;
    private String password;
}
