package com.carrental.service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.carrental.model.Car;
import com.carrental.model.Customer;
import com.carrental.model.Rental;
import com.carrental.repository.CarRepository;
import com.carrental.repository.CustomerRepository;
import com.carrental.repository.MaintenanceRepository;
import com.carrental.repository.RentalRepository;
import com.carrental.exception.CarNotAvailableException;
import com.carrental.exception.CarNotFoundException;
import com.carrental.exception.CustomerNotFoundException;
import com.carrental.exception.RentalNotFoundException;

@Service
public class RentalService {

    private RentalRepository rentalRepository;
    private CarRepository carRepository;
    private CustomerRepository customerRepository;
    private MaintenanceRepository maintenanceRepository;
    public RentalService(RentalRepository rentalRepository, CarRepository carRepository,CustomerRepository customerRepository,MaintenanceRepository maintenanceRepository) {
		this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.customerRepository=customerRepository;
        this.maintenanceRepository=maintenanceRepository;
    }

    public void addRental(Rental rental) {
        rentalRepository.save(rental);
    }

    @Transactional
    public Rental rentCar( int customerId, int carId,
            LocalDate startDate, LocalDate endDate) {
    	Car car = carRepository.findByIdForUpdate(carId)
    	        .orElseThrow(() ->
    	            new CarNotFoundException("Car not found with ID: " + carId)
    	        );
    	
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found with ID: " + customerId));
        
        long days = endDate.toEpochDay() - startDate.toEpochDay();

        double totalAmount = days * car.getPricePerDay();

        Rental rental = new Rental(
                
                customer,
                car,
                startDate,
                endDate,
                totalAmount,
                "ACTIVE"
        );


        if(rentalRepository.checkOverlap(carId, startDate, endDate)) {
        	throw new CarNotAvailableException("Car with id"+carId+" is already rented for the particular date.Try changing the dates to rent it");
        }
        if (maintenanceRepository.checkMaintenanceOverlap(
                carId, startDate, endDate)) {
            throw new CarNotAvailableException(
                    "Car is scheduled for maintenance during the requested dates.");
        }
        	rental=rentalRepository.save(rental);
        	return rental;        	 
    }

    public void returnCar(int rentalId) {

        Rental rental = getRental(rentalId);
        rental.setStatus("RETURNED");
        rentalRepository.save(rental);
    }

    public Rental getRental(int rentalId) {
        return rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                    new RentalNotFoundException(
                        "Rental with id " + rentalId + " not found"
                    )
                );
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
}