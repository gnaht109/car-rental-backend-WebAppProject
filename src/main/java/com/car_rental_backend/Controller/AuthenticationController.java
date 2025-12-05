package com.car_rental_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.dto.request.AuthenticationRequest;
import com.car_rental_backend.dto.request.IntrospectRequest;
import com.car_rental_backend.dto.response.AuthenticationResponse;
import com.car_rental_backend.service.AuthenticationService;
import com.car_rental_backend.dto.response.ApiResponse;
import com.car_rental_backend.dto.response.IntrospectResponse;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.nimbusds.jose.JOSEException;
import java.text.ParseException;


@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var data = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(data)
                .build();
    }
    
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var data = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(data)
                .build();
    }
}
