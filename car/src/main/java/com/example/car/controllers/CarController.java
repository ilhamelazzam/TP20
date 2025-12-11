package com.example.car.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.car.models.CarResponse;
import com.example.car.services.CarService;

@RestController
@RequestMapping("api/car")
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping
	public List<CarResponse> findAll() {
		return carService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			CarResponse car = carService.findById(id);
			return ResponseEntity.ok(car);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Erreur: " + e.getMessage());
		}
	}
}
