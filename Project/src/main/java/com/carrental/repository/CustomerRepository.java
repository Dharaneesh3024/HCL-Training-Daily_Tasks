package com.carrental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carrental.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByLicenseNumber(String licenseNumber);

    Optional<Customer> findByEmail(String email);
}