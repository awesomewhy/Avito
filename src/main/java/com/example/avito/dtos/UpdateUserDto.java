package com.example.avito.dtos;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String email;
    private String username;
    private String nickname;
    private String city;
}
