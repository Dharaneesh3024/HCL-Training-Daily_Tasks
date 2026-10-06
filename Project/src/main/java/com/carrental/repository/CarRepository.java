package com.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrental.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
	
}