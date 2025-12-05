package com.car_rental_backend.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.car_rental_backend.dto.request.UserCreationRequest;
import com.car_rental_backend.dto.response.UserResponse;
import com.car_rental_backend.enums.Role;
import com.car_rental_backend.exception.AppException;
import com.car_rental_backend.exception.ErrorCode;
import com.car_rental_backend.mapper.UserMapper;
import com.car_rental_backend.model.User;
import com.car_rental_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE,makeFinal= true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    
    //SignUp logic
    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        
        user.setRoles(null);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //Get all user info 
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    //Get user info by id
    public UserResponse getUser(Long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
    }

    
}
