package com.carrental.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.carrental.exception.CustomerNotFoundException;
import com.carrental.model.Customer;
import com.carrental.repository.CustomerRepository;


@Service
public class CustomerService {

	private static final Logger log =
	        LoggerFactory.getLogger(CustomerService.class);
	


	private CustomerRepository customerRepository;
	public CustomerService(CustomerRepository customerRepository) {
			this.customerRepository=customerRepository;
	}
	public Customer addCustomer(Customer customer) {
	    log.info("Adding customer: {}", customer.getName());
		customerRepository.save(customer);
		return customer;
	}
	public List<Customer> getCustomers(){
		return customerRepository.findAll();
	}
	public Customer getCustomer(int customerId) {
		log.warn("Customer with ID {} not found", customerId);
		return customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer with "+customerId+" not found"));
	}
	
	public void deleteCustomer(int id) {
			if(customerRepository.existsById(id)) {
				customerRepository.deleteById(id);
			}
			else {
				throw new CustomerNotFoundException("Customer with id "+id+" not found");
			}
			
	}
}
