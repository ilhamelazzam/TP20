package com.example.car.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
	private Long id;
	private String brand;
	private String model;
	private String matricule;
	private Client client;
}
