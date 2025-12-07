package com.car_rental_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCAUGHT_ERROR(9999, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "User already exists", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1003, "User not found", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(1004, "Incorrect password", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1402, "You do not have permission to access", HttpStatus.FORBIDDEN),
    CAR_EXISTED(1101,"This car is already registered", HttpStatus.BAD_REQUEST),
    CAR_NOT_FOUND(1102,"Car not found", HttpStatus.BAD_REQUEST) 
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    
}
