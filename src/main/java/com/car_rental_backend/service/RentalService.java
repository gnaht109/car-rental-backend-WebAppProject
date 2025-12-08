package com.car_rental_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.car_rental_backend.dto.request.RentalCreationRequest;
import com.car_rental_backend.dto.response.RentalResponse;
import com.car_rental_backend.enums.RentalStatus;
import com.car_rental_backend.exception.AppException;
import com.car_rental_backend.exception.ErrorCode;
import com.car_rental_backend.mapper.RentalMapper;
import com.car_rental_backend.model.Car;
import com.car_rental_backend.model.Rental;
import com.car_rental_backend.model.User;
import com.car_rental_backend.repository.CarRepository;
import com.car_rental_backend.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE,makeFinal= true)
public class RentalService {
    RentalRepository rentalRepository;
    CarRepository carRepository;
    RentalMapper rentalMapper;
    AuthContextService authContextService;

    public RentalResponse createRental(RentalCreationRequest request) {
        User client = authContextService.getCurrentUser();

        // 1. Fetch the car
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new AppException(ErrorCode.CAR_NOT_FOUND));

        // 2. Check if the car is available for the requested dates
        List<Rental> overlapping = rentalRepository.findOverlappingRentals(
                car.getId(),
                request.getStartDate(),
                request.getEndDate(),
                List.of(RentalStatus.PENDING, RentalStatus.ACTIVE)
        );

        if (!overlapping.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_DATE);
        }

        // 3. Map DTO to Rental entity
        Rental rental = rentalMapper.toRental(request);

        // 4. Set client, car, and initial status
        rental.setClient(client);
        rental.setCar(car);
        rental.setStatus(RentalStatus.PENDING);

        // 5. Save the rental and map to response
        return rentalMapper.toRentalResponse(rentalRepository.save(rental));
    }

    public List<RentalResponse> getRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toRentalResponse)
                .toList();
    }

    public RentalResponse getRental(Long id) {
        return rentalMapper.toRentalResponse(
                rentalRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.RENTAL_NOT_FOUND))
        );
    }

    public List<RentalResponse> getOwnRentals() {
        User user = authContextService.getCurrentUser();
        List<Rental> rentals = rentalRepository.findAllByClient(user);
        return rentals.stream()
                .map(rentalMapper::toRentalResponse)
                .toList();
    }

    public List<RentalResponse> getRentalsOfCar(Long carId){
        Car car = carRepository.findById(carId)
                .orElseThrow(()-> new AppException(ErrorCode.CAR_NOT_FOUND));
        
        List<Rental> rentals = rentalRepository.findAllByCar(car);
        return rentals.stream()
                .map(rentalMapper::toRentalResponse)
                .toList();
    }

    
}
