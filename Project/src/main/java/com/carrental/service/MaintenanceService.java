package com.carrental.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.carrental.dto.MaintenanceRequest;
import com.carrental.exception.CarNotAvailableException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.model.Car;
import com.carrental.model.Maintenance;
import com.carrental.repository.CarRepository;
import com.carrental.repository.MaintenanceRepository;

@Service
public class MaintenanceService {
	private final CarRepository carRepository;
	private final MaintenanceRepository maintenanceRepository;

	public MaintenanceService(CarRepository carRepository,
	                          MaintenanceRepository maintenanceRepository) {
	    this.carRepository = carRepository;
	    this.maintenanceRepository = maintenanceRepository;
	}
	
	public Maintenance scheduleMaintenance(MaintenanceRequest request) {
		Car car=carRepository.findById(request.getCarId()).orElseThrow(()-> new CarNotFoundException("Car not found"));
		LocalDate startDate=request.getStartDate();
		LocalDate endDate=request.getEndDate();
		  if (!endDate.isAfter(startDate)) {
		        throw new IllegalArgumentException(
		                "End date must be after start date");
		    }
		  if(maintenanceRepository.checkMaintenanceOverlap(request.getCarId(), startDate, endDate)) {
			  throw new CarNotAvailableException("Car not available at selected dates due to maintenance");
		  }
		  Maintenance maintenance=new Maintenance();
		  maintenance.setCar(car);
		    maintenance.setStartDate(startDate);
		    maintenance.setEndDate(endDate);
		    maintenance.setDescription(request.getDescription());
		   return maintenanceRepository.save(maintenance); 
		  
	}
}
