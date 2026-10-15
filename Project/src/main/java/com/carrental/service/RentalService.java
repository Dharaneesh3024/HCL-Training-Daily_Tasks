package com.carrental.service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    	if (startDate == null || endDate == null ||
    	        !endDate.isAfter(startDate)) {
    	    throw new IllegalArgumentException(
    	            "End date must be after start date");
    	}
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

    public Rental returnCar(int rentalId,double extraCharges,String returnCondition,
            String inspectionNotes) {
    	
    	
        Rental rental = getRental(rentalId);
        if(!"ACTIVE".equals(rental.getStatus())) {
        		throw new IllegalArgumentException("This rental is already returned or not available");
        }
        if (extraCharges < 0) {
            throw new IllegalArgumentException(
                    "Extra charges cannot be negative.");
        }

        rental.setExtraCharges(extraCharges);
        rental.setStatus("RETURNED");
        double total=rental.getTotalAmount();
        rental.setTotalAmount(total);
        rental.setReturnCondition(returnCondition);
        rental.setInspectionNotes(inspectionNotes);
        return rentalRepository.save(rental);
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
    
    public double getUtilisation(int carId, LocalDate startDate, LocalDate endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        long totalDays = endDate.toEpochDay() - startDate.toEpochDay();

        List<Rental> rentals = rentalRepository.findOverlappingRentals(
                carId, startDate, endDate);

        Set<LocalDate> bookedDates = new HashSet<>();

        for (Rental rental : rentals) {
            LocalDate overlapStart = rental.getStartDate().isAfter(startDate)
                    ? rental.getStartDate() : startDate;

            LocalDate overlapEnd = rental.getEndDate().isBefore(endDate)
                    ? rental.getEndDate() : endDate;

            for (LocalDate date = overlapStart;
                 date.isBefore(overlapEnd);
                 date = date.plusDays(1)) {
                bookedDates.add(date);
            }
        }

        long bookedDays = bookedDates.size();

        return Math.round((double) bookedDays / totalDays * 10000.0) / 100.0;
    }
}