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

    public RentalService(RentalRepository rentalRepository, CarRepository carRepository,CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.customerRepository=customerRepository;
    }

    public void addRental(Rental rental) {
        rentalRepository.save(rental);
    }

    @Transactional
    public Rental rentCar( int customerId, int carId,
            LocalDate startDate, LocalDate endDate) {
    	Car car = carRepository.findById(carId)
    	        .orElseThrow(() ->
    	            new CarNotFoundException("Car not found with ID: " + carId)
    	        );
    	
        if (!car.isAvailable()) {
            throw new CarNotAvailableException("Car is not available to rent");
        }
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


        car.setAvailable(false);
        carRepository.save(car);
        rental=rentalRepository.save(rental);
        return rental;
        
    }

    public void returnCar(int rentalId) {

        Rental rental = getRental(rentalId);

        Car car = rental.getCar();
        car.setAvailable(true);
        carRepository.save(car);

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