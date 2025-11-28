package com.car_rental_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.car_rental_backend.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = Exception.class) 
    ResponseEntity<ApiResponse> handlingException(Exception ex) {
        ApiResponse apiresponse = new ApiResponse();
        apiresponse.setCode(ErrorCode.UNCAUGHT_ERROR.getCode());
        apiresponse.setMessage(ErrorCode.UNCAUGHT_ERROR.getMessage());
        return ResponseEntity
                .badRequest()
                .body(apiresponse);
    }

    @ExceptionHandler(value = AppException.class) 
    ResponseEntity<ApiResponse> handlingAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .badRequest()
                .body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class) 
    ResponseEntity<String> handlingValidation(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getFieldError().getDefaultMessage());
    }
}
