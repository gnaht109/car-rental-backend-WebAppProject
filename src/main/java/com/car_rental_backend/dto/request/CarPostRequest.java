package com.car_rental_backend.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CarPostRequest {
    String brand;
    String model;
    int seat;
    String plate;
    double pricePerDay;
    List<String> imageUrls;

}
