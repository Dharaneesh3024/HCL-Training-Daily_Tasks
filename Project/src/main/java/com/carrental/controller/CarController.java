package com.carrental.controller;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.carrental.model.Car;
import com.carrental.service.CarService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
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
	public ResponseEntity<Car> getCar(@PathVariable int id) {
		Car car=carService.getCar(id);
		return ResponseEntity.status(HttpStatus.OK).body(car);
		
	}
	@PostMapping("/api/cars")
	public ResponseEntity<Car> addCar(@Valid @RequestBody Car car) {
		Car savedCar=carService.addCar(car);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCar); 
	}
	@DeleteMapping("/api/cars/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable int id) {
		carService.removeCar(id);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/api/cars/available")
	public List<Car> getAvailableCars(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
		return carService.getAvailableCars(startDate, endDate);
	}

@GetMapping("api/cars/branch/{branchId}")
public ResponseEntity<List<Car>> getCarsByBranch(
        @PathVariable int branchId) {
    return ResponseEntity.ok(carService.getCarsByBranch(branchId));
}

	
	
}

