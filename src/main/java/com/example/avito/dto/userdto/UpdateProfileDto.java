package com.example.avito.dto.userdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileDto {
    private String email;
    private String username;
    private String nickname;
    private String city;
}
