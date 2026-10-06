package com.carrental.service;
import com.carrental.repository.CarRepository;

import org.springframework.stereotype.Service;
import java.util.*;
import com.carrental.model.Car;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.CarNotAvailableException;

@Service
public class CarService {
	private CarRepository carRepository;
	
		public CarService(CarRepository carRepository) {

        this.carRepository=carRepository;
    }
	
	public void addCar(Car car) {
		carRepository.save(car);
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
}
