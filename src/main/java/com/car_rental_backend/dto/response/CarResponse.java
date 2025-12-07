package com.car_rental_backend.dto.response;

import java.util.List;

import com.car_rental_backend.enums.CarStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CarResponse {
    Long ownerId;
    Long id;
    String brand;
    String model;
    int seat;
    String plate;
    double pricePerDay;
    CarStatus status;
    List<String> imageUrls;

}

