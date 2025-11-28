package com.car_rental_backend.exception;

public enum ErrorCode {
    UNCAUGHT_ERROR(9999, "An unexpected error occurred"),
    USER_EXISTED(1001, "User already exists"),
    EMAIL_EXISTED(1002, "Email already exists"),
    USER_NOT_FOUND(1404, "User not found"),
    PASSWORD_INCORRECT(1403, "Incorrect password")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    private int code;
    private String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}
