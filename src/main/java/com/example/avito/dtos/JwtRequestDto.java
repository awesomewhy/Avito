package com.example.avito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequestDto {
    private Long id;
    private String email;
    private String password;
}
