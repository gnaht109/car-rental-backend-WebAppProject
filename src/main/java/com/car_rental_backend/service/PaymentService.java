package com.car_rental_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.car_rental_backend.exception.AppException;
import com.car_rental_backend.exception.ErrorCode;
import com.car_rental_backend.model.Rental;
import com.car_rental_backend.repository.RentalRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor

public class PaymentService {

    @Value("${stripe.secret-key}")
    String stripeSecretKey;

    RentalRepository rentalRepository;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String createPaymentIntent(Long rentalId) throws StripeException {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new AppException(ErrorCode.RENTAL_NOT_FOUND));

        long amount = rental.getTotalPrice() * 100; // VND → cents

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency("vnd")
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);

        return intent.getClientSecret();
    }
}

