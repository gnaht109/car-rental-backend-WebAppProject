package com.car_rental_backend.model;

import java.util.List;

import com.car_rental_backend.enums.CarStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Car {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String brand;
    String model;

    int seat;

    @Column(unique = true)
    String plate;


    double pricePerDay;

    @Enumerated(EnumType.STRING)
    CarStatus status = CarStatus.AVAILABLE;

    @ElementCollection
    @CollectionTable(name = "car_images", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "url")
    List<String> imageUrls;

}
