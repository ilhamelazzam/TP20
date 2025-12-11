package com.example.car;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.car.entities.Car;
import com.example.car.repositories.CarRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class CarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner initCars(CarRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Car(null, "Peugeot", "208", "AA-123-BB", 1L));
                repository.save(new Car(null, "Renault", "Clio", "BB-456-CC", 2L));
                repository.save(new Car(null, "Toyota", "Yaris", "CC-789-DD", 3L));
            }
        };
    }
}
