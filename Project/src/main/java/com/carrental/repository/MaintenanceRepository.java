package com.carrental.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carrental.model.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance,Integer>{
	@Query("SELECT CASE WHEN COUNT(m) >0 THEN true ELSE false END "+
			"FROM Maintenance m"+" WHERE m.car.id=:carId "+
			"AND m.startDate < :newEndDate "+
			"AND m.endDate > :newStartDate")
	boolean checkMaintenanceOverlap(
			@Param("carId") int carId,
			@Param(":newEndDate") LocalDate newStartDate,
			@Param(":newEndDate") LocalDate newEndDate
			);
}
