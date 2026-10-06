package com.carrental.service;

import java.time.LocalDate;

import java.util.*;

import org.springframework.stereotype.Service;

import com.carrental.model.Car;
import com.carrental.model.Customer;
import com.carrental.model.Rental;
import com.carrental.exception.CarNotAvailableException;
import com.carrental.exception.RentalNotFoundException;

@Service
public class RentalService {

    private HashMap<Integer, Rental> rentals = new HashMap<>();
    public void addRental(Rental rental) {
        rentals.put(rental.getId(), rental);
    }
    public void rentCar(int rentalId, Customer customer, Car car,
            LocalDate startDate, LocalDate endDate) {

if (!car.isAvailable()) {
throw new CarNotAvailableException("Car is not available to rent");
}

long days = endDate.toEpochDay() - startDate.toEpochDay();

double totalAmount = days * car.getPricePerDay();

Rental rental = new Rental(
rentalId,
customer,
car,
startDate,
endDate,
totalAmount,
"ACTIVE"
);

rentals.put(rentalId, rental);

car.setAvailable(false);

System.out.println("Car rented successfully.");
}
    public void returnCar(int rentalId) {

        Rental rental = rentals.get(rentalId);

        if (rental == null) {
            throw new RentalNotFoundException(
                "Rental not found with ID: " + rentalId
            );
        }

        Car car = rental.getCar();
        car.setAvailable(true);

        System.out.println("Car returned successfully.");
        rental.setStatus("RETURNED");
    }
    public Rental getRental(int rentalId) {
        return rentals.get(rentalId);
    }
    
    public List<Rental> getAllRentals() {
        return new ArrayList<>(rentals.values());
    }

}