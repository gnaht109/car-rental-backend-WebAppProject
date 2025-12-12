package com.car_rental_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.car_rental_backend.exception.AppException;
import com.car_rental_backend.exception.ErrorCode;
import com.car_rental_backend.model.User;
import com.car_rental_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthContextService {

    UserRepository userRepository;
    
    public Long getCurrentUserId() {
        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getToken().getClaim("userId");
    }

    public User getCurrentUser() {
        Long id = getCurrentUserId();
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}