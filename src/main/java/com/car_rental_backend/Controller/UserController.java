package com.car_rental_backend.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.car_rental_backend.dto.request.UserCreationRequest;
import com.car_rental_backend.dto.response.ApiResponse;
import com.car_rental_backend.dto.response.UserResponse;
import com.car_rental_backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin as needed
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") Long userId){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUser(userId))
                .build();
    }

    //Get own user info endpoint
    @GetMapping("/me")
    ApiResponse<UserResponse> getUserInfo(){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserInfo())
                .build();
    }
    
    //SignUp endpoint
    @PostMapping("/signup")
    ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .build();
    }
}
