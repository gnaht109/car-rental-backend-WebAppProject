package com.car_rental_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.dto.request.CarPostRequest;
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
    

    //Post Car endpoint
    @PostMapping("/post")
    ApiResponse<CarResponse> postCar(@Valid @RequestBody CarPostRequest request) {
        return ApiResponse.<CarResponse>builder()
                .data(carService.postCar(request))
                .build();
    }

    // @GetMapping("/{carId}")


}
