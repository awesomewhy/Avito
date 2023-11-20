package com.example.avito.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDto {
    private String email;
    private String name;
    private String password;
}
