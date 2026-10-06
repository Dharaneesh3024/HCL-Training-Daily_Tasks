package com.carrental.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.carrental.model.Rental;

public interface RentalRepository extends JpaRepository<Rental,Integer>{

}
