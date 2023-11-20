package com.example.avito.dto.userdto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;
}
