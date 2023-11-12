package com.example.avito.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;
}
