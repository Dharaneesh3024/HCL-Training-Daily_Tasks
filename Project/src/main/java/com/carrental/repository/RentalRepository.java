package com.carrental.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carrental.model.Rental;


public interface RentalRepository extends JpaRepository<Rental,Integer>{
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
		       "FROM Rental r " +
		       "WHERE r.car.id = :carId " +
		       "AND r.status = 'ACTIVE' " +
		       "AND r.startDate < :newEndDate " +
		       "AND r.endDate > :newStartDate")
		boolean checkOverlap(
		        @Param("carId") int carId,
		        @Param("newStartDate") LocalDate newStartDate,
		        @Param("newEndDate") LocalDate newEndDate);
	
}
