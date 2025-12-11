package com.example.car.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.car.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
