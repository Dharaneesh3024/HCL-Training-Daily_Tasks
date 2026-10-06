package com.carrental.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.model.Customer;
import com.carrental.repository.CustomerRepository;

@RestController
public class CustomerController {
	private CustomerRepository customerRepository;
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository=customerRepository;
	}
	
	@PostMapping("/api/customers")
	public void addCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}
	
	@GetMapping("/api/customers")
	public List<Customer> getCustomers() {
		return customerRepository.findAll()	;
		}
}
