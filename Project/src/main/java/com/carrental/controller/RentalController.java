package com.carrental.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.carrental.model.Rental;
import com.carrental.model.RentalRequest;
import com.carrental.service.RentalService;

import jakarta.validation.Valid;

//import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestBody;
//import com.carrental.model.Car;
//import com.carrental.model.Customer;


@RestController
public class RentalController {
	private RentalService rentalService;
	public RentalController(RentalService rentalService) {
		this.rentalService=rentalService;
	}
	
	@GetMapping("/api/rentals")
	public List<Rental> getAllRentals(){
		return rentalService.getAllRentals();
	}
	@PostMapping("/api/rentals")
	public ResponseEntity<Rental> createRental(@Valid @RequestBody RentalRequest request) {
		Rental rental=rentalService.rentCar(
		        request.getCustomerId(),
		        request.getCarId(),
		        request.getStartDate(),
		        request.getEndDate());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(rental);
		
	}
	
@PutMapping("/api/rentals/{id}/return")
public ResponseEntity<Void> returnCar(@PathVariable int id) {
	rentalService.returnCar(id);
	return ResponseEntity.noContent().build();
}
	
}
