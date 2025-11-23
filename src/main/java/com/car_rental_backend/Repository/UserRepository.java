package com.car_rental_backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car_rental_backend.Model.User;

public interface  UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
