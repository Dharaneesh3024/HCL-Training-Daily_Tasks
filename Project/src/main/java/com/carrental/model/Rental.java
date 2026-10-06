package com.carrental.model;

import java.time.LocalDate;

public class Rental {
	private int id;
	private Customer customer;
	private Car car;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalAmount;
	private String status="ACTIVE";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Rental(int id, Customer customer, Car car, LocalDate startDate, LocalDate endDate, double totalAmount,String status) {
		super();
		this.id = id;
		this.customer = customer;
		this.car = car;
		this.startDate = startDate;
		this.endDate = endDate;
		this.totalAmount = totalAmount;
		this.status=status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
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
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
	    return "Rental [id=" + id + ", customer=" + customer + ", car=" + car
	            + ", totalAmount=" + totalAmount + ", status=" + status + "]";
	}
	
	
}
