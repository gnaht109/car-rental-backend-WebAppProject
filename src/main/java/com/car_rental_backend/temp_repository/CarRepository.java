package com.car_rental_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car_rental_backend.model.Car;
import com.car_rental_backend.model.User;



public interface CarRepository extends JpaRepository<Car, Long>{
    boolean existsByPlate(String plate);
    List<Car> findAllByOwner(User owner);
}
