package com.example.avito.dto.jwtdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDto {
    private Long id;
    private String email;
    private String password;
}
