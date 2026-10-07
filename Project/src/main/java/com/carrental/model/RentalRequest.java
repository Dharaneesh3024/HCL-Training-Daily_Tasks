package com.carrental.model;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import com.carrental.validation.ValidRentalDates;

@ValidRentalDates
public class RentalRequest {
	
    private int rentalId;
	@Positive(message="Positive field")
    private int customerId;
	@Positive(message="Positive field")
    private int carId;
	@NotNull(message="Null values not allowed")
    private LocalDate startDate;
	@NotNull(message="Null values not allowed")
    private LocalDate endDate;
    
       
    public RentalRequest() {
    }
   

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}