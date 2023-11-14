package com.example.avito.validation;

import java.util.regex.Pattern;

public class EmailValidation {
    public static boolean isValidEmailAddress(String email) {
        String emailRegexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(emailRegexp).matcher(email).matches();
    }
}
