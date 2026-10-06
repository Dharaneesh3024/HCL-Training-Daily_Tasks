package com.carrental.service;
import org.springframework.stereotype.Service;
import java.util.*;
import com.carrental.model.Car;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.CarNotAvailableException;

@Service
public class CarService {
	private HashMap<Integer,Car> cars=new HashMap<>();
	public CarService() {

        Car car1 = new Car(101, "Toyota", "Camry", 2500, true);
        Car car2 = new Car(102, "Honda", "City", 1800, true);
        Car car3 = new Car(103, "Hyundai", "Creta", 2200, true);

        addCar(car1);
        addCar(car2);
        addCar(car3);
    }
	
	public void addCar(Car car) {
		cars.put(car.getId(),car);
	}
	public Car getCar(int id) {
	    Car car = cars.get(id);

	    if (!cars.containsKey(id)) {
	        throw new CarNotFoundException("Car not found with ID: " + id);
	    }

	    return car;
	}
	public List<Car> getAllCars(){
		return new ArrayList<>(cars.values());
	}
	public void removeCar(int id) {
		if(cars.containsKey(id)) {
			cars.remove(id);
			System.out.print("Car "+ id+ " removed");	
		}
		else {
			System.out.print("Car not found");
		}
	}
	public void rentCar(int carId) {

	    Car car = getCar(carId);

	    if (car != null && car.isAvailable()) {
	        car.setAvailable(false);
	        System.out.println("Car rented successfully.");
	    } else {
	        throw new CarNotAvailableException("Car is not available");
	    }
	}
	public void returnCar(int carId) {

	    Car car = cars.get(carId);

	    if (car != null) {
	        car.setAvailable(true);
	        System.out.println("Car returned successfully.");
	    } else {
	        System.out.println("Car not found.");
	    }
	}
}
