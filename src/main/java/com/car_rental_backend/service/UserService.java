package com.car_rental_backend.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.car_rental_backend.dto.request.UpdateUserRequest;
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
    AuthContextService authContextService;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    
    //SignUp logic
    public UserResponse createUser(UserCreationRequest request){//
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //Get all user info 
    @PreAuthorize("hasRole('ADMIN')")  //kiem tra truoc khi vao method
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    //Get user info by id
    @PreAuthorize("hasRole('ADMIN')") 
    public UserResponse getUser(Long id) {
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
        );
    }


    //Get own user info
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserResponse getUserInfo() {
        User user = authContextService.getCurrentUser();
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    //update User
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User current = authContextService.getCurrentUser();
        boolean isAdmin = current.getRoles().contains(Role.ADMIN.name());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        //Nếu không phải admin hoặc nếu current không phải user đó
        if (!isAdmin && !current.getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // username
        if (request.getUsername() != null) {
            if (userRepository.existsByUsername(request.getUsername())
                    && !request.getUsername().equals(user.getUsername())) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
            user.setUsername(request.getUsername());
        }

        // email
        if (request.getEmail() != null) {
            if (userRepository.existsByEmail(request.getEmail())
                    && !request.getEmail().equals(user.getEmail())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }
            user.setEmail(request.getEmail());
        }

        // phone
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    
}
