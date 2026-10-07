package com.carrental.controller;
import java.util.HashMap;

import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.carrental.model.Car;
import com.carrental.service.CarService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CarController {
	private CarService carService;
	  public CarController(CarService carService) {
	        this.carService = carService;
	    }
	  
	@GetMapping("/api/cars")
	public List<Car> getCars() {
		return carService.getAllCars();
	}
	@GetMapping("/api/cars/{id}")
	public Car getCar(@PathVariable int id) {
		return carService.getCar(id);
	}
	@PostMapping("/api/cars")
	public void addCar(@Valid @RequestBody Car car) {
		carService.addCar(car);
	}
	@DeleteMapping("/api/cars/{id}")
	public void deleteCar(@PathVariable int id) {
		carService.removeCar(id);
	}
	
}

