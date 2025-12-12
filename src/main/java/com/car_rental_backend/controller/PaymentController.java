package com.car_rental_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car_rental_backend.dto.response.ApiResponse;
import com.car_rental_backend.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the origin as needed
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    @PostMapping("/create/{rentalId}")
    ApiResponse<String> createIntent(@PathVariable Long rentalId) {
        String clientSecret = paymentService.createPaymentIntent(rentalId);
        return ApiResponse.<String>builder().data(clientSecret).build();
    }

}
