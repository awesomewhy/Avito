package com.example.avito.enums;

public enum ReviewServiceError {
    COMMENT_NOT_ADDED("comment not added"),
    COMMENT_ADDED("comment added"),
    USER_NOT_FOUND("user not found");

    private final String message;

    ReviewServiceError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
