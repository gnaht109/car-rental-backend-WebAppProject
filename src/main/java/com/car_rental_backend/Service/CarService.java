package com.car_rental_backend.service;

import org.springframework.stereotype.Service;

import com.car_rental_backend.dto.request.CarPostRequest;
import com.car_rental_backend.dto.response.CarResponse;
import com.car_rental_backend.exception.AppException;
import com.car_rental_backend.exception.ErrorCode;
import com.car_rental_backend.mapper.CarMapper;
import com.car_rental_backend.model.Car;
import com.car_rental_backend.model.User;
import com.car_rental_backend.repository.CarRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE,makeFinal= true)
public class CarService {
    CarRepository carRepository;
    AuthContextService authContextService;
    CarMapper carMapper;

    //Post car
    public CarResponse postCar(CarPostRequest request){
        if(carRepository.existsByPlate(request.getPlate()))
            throw new AppException(ErrorCode.CAR_EXISTED);

        User owner = authContextService.getCurrentUser();

        Car car = carMapper.toCar(request);
        car.setOwner(owner);

        return carMapper.toCarResponse(carRepository.save(car));
    }
}
