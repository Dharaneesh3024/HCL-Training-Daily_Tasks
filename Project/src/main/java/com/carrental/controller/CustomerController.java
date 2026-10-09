package com.carrental.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.model.Customer;
import com.carrental.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class CustomerController {
	private CustomerService customerService;
	public CustomerController(CustomerService customerService) {
		this.customerService=customerService;
	}
	
	@PostMapping("/api/customers")
	@Operation(summary="Add a customer")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer) {
		Customer savedCustomer=customerService.addCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
		
	}
	
	@GetMapping("/api/customers")
	@Operation(summary = "Get all customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
		}
	
	@GetMapping("/api/customers/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable int id){
		Customer responseCustomer=customerService.getCustomer(id);
		return ResponseEntity.ok(responseCustomer);
	}
	
	@DeleteMapping("/api/customers/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable int id){
		customerService.deleteCustomer(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
