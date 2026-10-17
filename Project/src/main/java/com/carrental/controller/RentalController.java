package com.carrental.controller;

import java.time.LocalDate;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.model.PickupInspectionRequest;
import com.carrental.model.Rental;
import com.carrental.model.RentalRequest;
import com.carrental.model.RentalReturnRequest;
import com.carrental.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
public ResponseEntity<Rental> returnCar(@PathVariable int id, @Valid @RequestBody RentalReturnRequest request) {
	Rental rental=rentalService.returnCar(id,request.getExtraCharges(),request.getReturnCondition(),request.getInspectionNotes());
	return ResponseEntity.ok(rental);
}

@GetMapping("/api/rentals/utilisation/{carId}")
public ResponseEntity<Double> getUtilisation(@PathVariable int carId,@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
	double utilisation=rentalService.getUtilisation(carId, startDate, endDate);
	return ResponseEntity.ok(utilisation);
}
@PutMapping("/api/rentals/{id}/pickup")
public ResponseEntity<Rental> recordPickupInspection(@PathVariable int id,@Valid @RequestBody PickupInspectionRequest request){
	Rental rental=rentalService.recordPickupInspection(id, request);
	return ResponseEntity.ok(rental);
}
@GetMapping("/api/rentals/{id}")
public ResponseEntity<Rental> getRental(@PathVariable int id) {
    Rental rental = rentalService.getRental(id);
    return ResponseEntity.ok(rental);
}
@DeleteMapping("/api/rentals/{id}")
	public ResponseEntity<Void> deleteRental(@PathVariable int id){
		rentalService.deleteRental(id);
		return ResponseEntity.noContent().build();
}
}
