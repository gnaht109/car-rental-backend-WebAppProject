package com.car_rental_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car_rental_backend.model.Car;
import com.car_rental_backend.model.Rental;
import com.car_rental_backend.model.User;

public interface RentalRepository extends JpaRepository<Rental, Long>{
    List<Rental> findAllByCar(Car car);
    List<Rental> findAllByUser(User user);
    <Optional>Rental findByRentalId(Long id);
}
