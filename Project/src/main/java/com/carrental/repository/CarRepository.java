package com.carrental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carrental.model.Car;

import jakarta.persistence.LockModeType;

public interface CarRepository extends JpaRepository<Car, Integer> {
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT c FROM Car c WHERE c.id = :carId")
	Optional<Car> findByIdForUpdate(@Param("carId") int carId);
}