package com.car_rental_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.car_rental_backend.dto.request.CarPostRequest;
import com.car_rental_backend.dto.response.CarResponse;
import com.car_rental_backend.model.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car toCar(CarPostRequest request);

    @Mapping(source = "owner.id", target = "ownerId")
    CarResponse toCarResponse(Car car);
}
