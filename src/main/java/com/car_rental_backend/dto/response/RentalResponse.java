package com.car_rental_backend.dto.response;

import java.time.LocalDateTime;

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
public class RentalResponse {
    Long rentalId;
    Long clientId;
    Long carId;
    String carModel;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String status;
    long totalPrice;
}
