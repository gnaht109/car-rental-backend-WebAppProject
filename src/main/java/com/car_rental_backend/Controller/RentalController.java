package com.car_rental_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.dto.request.RentalCreationRequest;
import com.car_rental_backend.dto.response.ApiResponse;
import com.car_rental_backend.dto.response.CarResponse;
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

    @GetMapping
    ApiResponse<List<RentalResponse>> getRentals(){
        return ApiResponse.<List<RentalResponse>>builder()
                .data(rentalService.getRentals())
                .build();
    }
}
