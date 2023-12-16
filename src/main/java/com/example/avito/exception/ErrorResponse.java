package com.example.avito.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends Exception{
    private final int code;
    private final String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
