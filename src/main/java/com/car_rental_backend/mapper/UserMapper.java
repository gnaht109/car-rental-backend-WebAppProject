package com.car_rental_backend.mapper;

import org.mapstruct.Mapper;

import com.car_rental_backend.dto.request.UserCreationRequest;
import com.car_rental_backend.dto.response.UserResponse;
import com.car_rental_backend.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
