package com.carrental.model;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
import com.carrental.validation.ValidRentalDates;

@ValidRentalDates
public class RentalRequest {
	
	@NotNull(message="Null values not allowed")
	@Positive(message="Positive field")
    private Integer customerId;
	
	@NotNull(message="Null values not allowed")
	@Positive(message="Positive field")
    private Integer carId;
	
	@NotNull(message="Null values not allowed")
    private LocalDate startDate;
	@NotNull(message="Null values not allowed")
    private LocalDate endDate;
    
       
    public RentalRequest() {
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