package com.car_rental_backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.car_rental_backend.enums.RentalStatus;
import com.car_rental_backend.model.Car;
import com.car_rental_backend.model.Rental;
import com.car_rental_backend.model.User;

public interface RentalRepository extends JpaRepository<Rental, Long>{
    List<Rental> findAllByCar(Car car);
    List<Rental> findAllByClient(User client);

    @EntityGraph(attributePaths = {"client", "car", "car.owner"})
    Optional<Rental> findById(Long id);

    @Query("SELECT r FROM Rental r WHERE r.car.id = :carId AND r.status IN :statuses AND " +
           "(:startDate < r.endDate AND :endDate > r.startDate)")
    List<Rental> findOverlappingRentals(
        @Param("carId") Long carId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("statuses") List<RentalStatus> statuses
    );

    @Query("""
    SELECT r FROM Rental r
    WHERE r.status = 'ACTIVE'
      AND r.endDate < CURRENT_DATE
    """)
    List<Rental> findExpiredActiveRentals();
}
