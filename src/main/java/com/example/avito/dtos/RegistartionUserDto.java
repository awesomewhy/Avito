package com.example.avito.dtos;

import com.example.avito.entity.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
public class RegistartionUserDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String role;
}
