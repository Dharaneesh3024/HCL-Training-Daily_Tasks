package com.carrental.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.dto.MaintenanceRequest;
import com.carrental.exception.CarNotAvailableException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.MaintenanceNotFoundException;
import com.carrental.model.Car;
import com.carrental.model.Maintenance;
import com.carrental.repository.CarRepository;
import com.carrental.repository.MaintenanceRepository;
import com.carrental.repository.RentalRepository;

@Service
public class MaintenanceService {
	private final CarRepository carRepository;
	private final MaintenanceRepository maintenanceRepository;
	private final RentalRepository rentalRepository;

	public MaintenanceService(CarRepository carRepository,
	                          MaintenanceRepository maintenanceRepository,RentalRepository rentalRepository) {
	    this.carRepository = carRepository;
	    this.maintenanceRepository = maintenanceRepository;
	    this.rentalRepository=rentalRepository;
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
		  if(rentalRepository.checkOverlap(car.getId(), startDate, endDate)) {
			  throw new CarNotAvailableException("Car not available to rent");
		  }
		  Maintenance maintenance=new Maintenance();
		  maintenance.setCar(car);
		    maintenance.setStartDate(startDate);
		    maintenance.setEndDate(endDate);
		    maintenance.setDescription(request.getDescription());
		   return maintenanceRepository.save(maintenance); 
		  
	}
	
	public List<Maintenance> getAllMaintenance(){
		return maintenanceRepository.findAll();
	}
	
	public Maintenance getMaintenance(int id) {
		return maintenanceRepository.findById(id).orElseThrow(()-> new MaintenanceNotFoundException("Maintenance not found"));
	}
	
	public void removeMaintenance(int id) {
		if(maintenanceRepository.existsById(id)) {
			maintenanceRepository.deleteById(id);
		}
		else {
			throw new MaintenanceNotFoundException("Maintenance not found");
		}
		
	}
}
