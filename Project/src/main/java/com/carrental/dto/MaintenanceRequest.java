package com.carrental.dto;

import java.time.LocalDate;

public class MaintenanceRequest {

    private int carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    public MaintenanceRequest(int carId, LocalDate startDate, LocalDate endDate, String description) {
		super();
		this.carId = carId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}