package com.car_rental_backend.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.car_rental_backend.dto.request.CarPostRequest;
import com.car_rental_backend.dto.request.CarStatusUpdateRequest;
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


    public boolean isOwner(Long carId) {
    User currentUser = authContextService.getCurrentUser();
    return carRepository.findById(carId)
            .map(car -> car.getOwner().getId().equals(currentUser.getId()))
            .orElse(false);
    }
    //Post car
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CarResponse postCar(CarPostRequest request){
        if(carRepository.existsByPlate(request.getPlate()))
            throw new AppException(ErrorCode.CAR_EXISTED);

        User owner = authContextService.getCurrentUser();

        Car car = carMapper.toCar(request);
        car.setOwner(owner);

        return carMapper.toCarResponse(carRepository.save(car));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<CarResponse> getCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toCarResponse)
                .toList();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CarResponse getCar(Long id) {
        return carMapper.toCarResponse(
                carRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND))
        );
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<CarResponse> getOwnCars() {
        User user = authContextService.getCurrentUser();
        List<Car> cars = carRepository.findAllByOwner(user);
        return cars.stream()
                .map(carMapper::toCarResponse)
                .toList();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CarResponse updateStatus(Long carId, CarStatusUpdateRequest request) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND));

        car.setStatus(request.getStatus());

        return carMapper.toCarResponse(carRepository.save(car));
    }

    @PreAuthorize("hasRole('ADMIN') or @carService.isOwner(#carId)")
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND));

        carRepository.delete(car);
    }

    
}
