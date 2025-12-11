package com.car_rental_backend.controller;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.dto.request.CarPostRequest;
import com.car_rental_backend.dto.request.CarStatusUpdateRequest;
import com.car_rental_backend.dto.response.ApiResponse;
import com.car_rental_backend.dto.response.CarResponse;
import com.car_rental_backend.service.CarService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin as needed
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CarController {
    CarService carService;

    // Get all cars
    @GetMapping
    ApiResponse<List<CarResponse>> getCars(){
        return ApiResponse.<List<CarResponse>>builder()
                .data(carService.getCars())
                .build();
    }

    //Post Car endpoint
    @PostMapping
    ApiResponse<CarResponse> postCar(@Valid @RequestBody CarPostRequest request) {
        return ApiResponse.<CarResponse>builder()
                .data(carService.postCar(request))
                .build();
    }

    @GetMapping("/{carId}")
    ApiResponse<CarResponse> getCar(@PathVariable("carId") Long carId){
        return ApiResponse.<CarResponse>builder()
                .data(carService.getCar(carId))
                .build();
    }

    @GetMapping("/me")
    ApiResponse<List<CarResponse>> getOwnCars(){
        return ApiResponse.<List<CarResponse>>builder()
                .data(carService.getOwnCars())
                .build();
    }


    //UPDATE CAR STATUS
    @PatchMapping("/{carId}/status")
    ApiResponse<CarResponse> updateCarStatus(
            @PathVariable("carId") Long carId,
            @Valid @RequestBody CarStatusUpdateRequest request
    ) {
        return ApiResponse.<CarResponse>builder()
                .data(carService.updateStatus(carId, request))
                .build();
    }

    //DELETE CAR
    @DeleteMapping("/{carId}")
    ApiResponse<Void> deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId); // call the service, no return value

        return ApiResponse.<Void>builder()
                .build(); // no data field for Void
    }

}
