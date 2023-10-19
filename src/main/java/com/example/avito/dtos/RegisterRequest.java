package com.example.avito.dtos;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String name;
    private String password;

}
