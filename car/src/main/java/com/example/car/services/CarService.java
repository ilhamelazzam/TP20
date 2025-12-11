package com.example.car.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.repositories.CarRepository;

@Service
public class CarService {

	private static final Logger log = LoggerFactory.getLogger(CarService.class);
	private static final String CLIENT_SERVICE_URL = "http://localhost:8888/SERVICE-CLIENT/api/client/";

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private RestTemplate restTemplate;

	public List<CarResponse> findAll() {
		return carRepository.findAll().stream()
				.map(this::mapToCarResponse)
				.collect(Collectors.toList());
	}

	public CarResponse findById(Long id) throws Exception {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new Exception("Voiture non trouvée avec l'ID: " + id));
		return mapToCarResponse(car);
	}

	private CarResponse mapToCarResponse(Car car) {
		Client client = null;
		try {
			client = restTemplate.getForObject(CLIENT_SERVICE_URL + car.getClient_id(), Client.class);
		} catch (Exception e) {
			log.warn("Impossible de récupérer le client {} pour la voiture {}: {}", car.getClient_id(), car.getId(), e.getMessage());
		}

		return CarResponse.builder()
				.id(car.getId())
				.brand(car.getBrand())
				.model(car.getModel())
				.matricule(car.getMatricule())
				.client(client)
				.build();
	}
}
