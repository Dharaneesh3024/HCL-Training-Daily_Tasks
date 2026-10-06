package com.carrental.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.carrental.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

}
