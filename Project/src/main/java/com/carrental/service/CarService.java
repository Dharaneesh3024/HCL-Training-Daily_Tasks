package com.carrental.service;
import com.carrental.repository.CarRepository;
import com.carrental.repository.RentalRepository;
import com.carrental.repository.MaintenanceRepository;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import java.util.*;
import com.carrental.model.Car;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.CarNotAvailableException;

@Service
public class CarService {
	private CarRepository carRepository;
	private RentalRepository rentalRepository;
	private MaintenanceRepository maintenanceRepository;
	
	public CarService(CarRepository carRepository,
            RentalRepository rentalRepository,
            MaintenanceRepository maintenanceRepository) {
this.carRepository = carRepository;
this.rentalRepository = rentalRepository;
this.maintenanceRepository = maintenanceRepository;
}
	
	public Car addCar(Car car) {
		return carRepository.save(car);
	}
	public Car getCar(int id) {
		return carRepository.findById(id)
	            .orElseThrow(() ->
	                new CarNotFoundException("Car not found with ID: " + id));
	}
	
	public List<Car> getAllCars(){
		return carRepository.findAll();
		}
	
	public void removeCar(int id) {
		if(!carRepository.existsById(id)) {
			throw new CarNotFoundException("Car not found with ID: " + id);	
		}
		
			carRepository.deleteById(id);
		
	}
	
	public void rentCar(int carId) {

	    Car car = getCar(carId);

	    if(!car.isAvailable()) {
	    	throw new CarNotAvailableException("Car with id "+carId+" is not available");
	    }
	    car.setAvailable(false);
	    carRepository.save(car);
	    
	}
	public void returnCar(int carId) {

	    Car car = getCar(carId);
	    car.setAvailable(true);
	    carRepository.save(car);
	  
	}
	
	public List<Car> getAvailableCars(LocalDate startDate,LocalDate endDate){
			if(startDate==null || endDate==null || !endDate.isAfter(startDate)) {
				throw new IllegalArgumentException("Not suitable values provided");
			}
			List<Car> availableCars=new ArrayList<>();
			for(Car car:carRepository.findAll()) {
				boolean rentalConflict=rentalRepository.checkOverlap(car.getId(), startDate, endDate);
				boolean maintenanceConflict=maintenanceRepository.checkMaintenanceOverlap(car.getId(), startDate, endDate);
				if(!rentalConflict && !maintenanceConflict) {
					availableCars.add(car);
				}
				
			}
			return availableCars;
	}
	  public List<Car> getCarsByBranch(int branchId){
	    	return carRepository.findByBranchId(branchId);
	    }
}
