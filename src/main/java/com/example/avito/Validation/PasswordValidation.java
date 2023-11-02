package com.example.avito.Validation;

import javax.validation.constraints.Pattern;

public class PasswordValidation {
    public static boolean isvalidPassword(String email) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        return email.matches(passwordRegex);
    }
}
