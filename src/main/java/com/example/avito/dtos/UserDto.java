package com.example.avito.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
}
