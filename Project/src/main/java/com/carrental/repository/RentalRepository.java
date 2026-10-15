package com.carrental.repository;

import java.time.LocalDate;
import java.util.List;

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
	
	@Query("SELECT COALESCE(SUM(" +
		       "FUNCTION('DATEDIFF', " +
		       "LEAST(r.endDate, :endDate), " +
		       "GREATEST(r.startDate, :startDate)" +
		       ")), 0) " +
		       "FROM Rental r " +
		       "WHERE r.car.id = :carId " +
		       "AND r.status IN ('ACTIVE', 'RETURNED') " +
		       "AND r.startDate < :endDate " +
		       "AND r.endDate > :startDate")
		long getBookedDays(
		        @Param("carId") int carId,
		        @Param("startDate") LocalDate startDate,
		        @Param("endDate") LocalDate endDate);
	
    @Query("SELECT r FROM Rental r " +
            "WHERE r.car.id = :carId " +
            "AND r.status IN ('ACTIVE', 'RETURNED') " +
            "AND r.startDate < :endDate " +
            "AND r.endDate > :startDate")
     List<Rental> findOverlappingRentals(
             @Param("carId") int carId,
             @Param("startDate") LocalDate startDate,
             @Param("endDate") LocalDate endDate);
	
}
