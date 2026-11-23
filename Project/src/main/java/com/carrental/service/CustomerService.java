
package com.carrental.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.carrental.exception.CustomerNotFoundException;
import com.carrental.model.Customer;
import com.carrental.repository.CustomerRepository;
import com.carrental.repository.RentalRepository;

@Service
public class CustomerService {

    private static final Logger log =
            LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;
    private RentalRepository rentalRepository;

    public CustomerService(CustomerRepository customerRepository,
                           RentalRepository rentalRepository) {
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    private String getLoggedInEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    public Customer addCustomer(Customer customer) {
        log.info("Adding customer: {}", customer.getName());

        if (customerRepository.existsByLicenseNumber(
                customer.getLicenseNumber())) {
            throw new IllegalArgumentException(
                    "Duplicate license number found!! Kindly verify it.");
        }

        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(int customerId) {
        log.warn("Customer with ID {} not found", customerId);

        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer with " + customerId + " not found"));
    }

    public Customer getMyProfile() {
        return customerRepository.findByEmail(getLoggedInEmail())
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer profile not found for this account"));
    }

    public Customer updateMyProfile(Customer updatedCustomer) {

        Customer customer = getMyProfile();

        if (!customer.getLicenseNumber().equals(
                updatedCustomer.getLicenseNumber()) &&
                customerRepository.existsByLicenseNumber(
                        updatedCustomer.getLicenseNumber())) {
            throw new IllegalArgumentException(
                    "Duplicate license number found!! Kindly verify it.");
        }

        customer.setName(updatedCustomer.getName());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setLicenseNumber(updatedCustomer.getLicenseNumber());
        customer.setLicenseExpiryDate(
                updatedCustomer.getLicenseExpiryDate());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(int id) {

        if (customerRepository.existsById(id)) {
            if (rentalRepository.existsByCustomerId(id)) {
                throw new IllegalArgumentException(
                        "Customer associated with rental and can't be deleted");
            } else {
                customerRepository.deleteById(id);
            }
        } else {
            throw new CustomerNotFoundException(
                    "Customer with id " + id + " not found");
        }
    }
}
