package com.example.avito.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    private String email;
    private String username;
    private String nickname;
    private String city;
}
