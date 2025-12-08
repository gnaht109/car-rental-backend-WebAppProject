package com.car_rental_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.dto.request.RentalCreationRequest;
import com.car_rental_backend.dto.response.ApiResponse;
import com.car_rental_backend.dto.response.RentalResponse;
import com.car_rental_backend.service.RentalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin as needed
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RentalController {
    RentalService rentalService;


    //create Rental
    @PostMapping
    ApiResponse<RentalResponse> createRental(@Valid @RequestBody RentalCreationRequest request) {
        return ApiResponse.<RentalResponse>builder()
                .data(rentalService.createRental(request))
                .build();
    }

    //Get all rental 
    @GetMapping
    ApiResponse<List<RentalResponse>> getRentals(){
        return ApiResponse.<List<RentalResponse>>builder()
                .data(rentalService.getRentals())
                .build();
    }

    //Get a rental by id
    @GetMapping("{rentalId}")
    ApiResponse<RentalResponse> getRental(@PathVariable Long rentalId){
        return ApiResponse.<RentalResponse>builder()
                .data(rentalService.getRental(rentalId))
                .build();
    }

    @GetMapping("/me")
    ApiResponse<List<RentalResponse>> getOwnRentals(){
        return ApiResponse.<List<RentalResponse>>builder()
                .data(rentalService.getOwnRentals())
                .build();
    }

    @GetMapping("/cars/{carId}")
    ApiResponse<List<RentalResponse>> getRentalsOfCar(@PathVariable Long carId) {
        return ApiResponse.<List<RentalResponse>>builder()
                .data(rentalService.getRentalsOfCar(carId))
                .build();
    }
}
