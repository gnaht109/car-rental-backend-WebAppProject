package com.car_rental_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.car_rental_backend.dto.request.RentalCreationRequest;
import com.car_rental_backend.dto.response.RentalResponse;
import com.car_rental_backend.model.Rental;

@Mapper(componentModel = "spring")
public interface RentalMapper {
    Rental toRental(RentalCreationRequest request);

    @Mapping(source = "client.id", target = "clientId")
    RentalResponse toRentalResponse(Rental rental);
}
