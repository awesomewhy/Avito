package com.example.avito.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private Long id;
    private String username;
    private String password;

}
