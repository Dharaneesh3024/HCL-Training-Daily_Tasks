package com.carrental.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Car car;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalAmount;
	private String status="ACTIVE";
	private double extraCharges;
	private String returnCondition;
	private String inspectionNotes;
	public Rental() {
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Rental( Customer customer, Car car, LocalDate startDate, LocalDate endDate, double totalAmount,String status) {
		super();
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
	public void setExtraCharges(double extraCharges) {
		this.extraCharges=extraCharges;
	}
	public double getExtraCharges() {
		return extraCharges;
	}
	public double getFinalAmount() {
		return totalAmount+extraCharges;
	}
	public String getReturnCondition() {
		return returnCondition;
	}
	public void setReturnCondition(String returnCondition) {
		this.returnCondition = returnCondition;
	}
	public String getInspectionNotes() {
		return inspectionNotes;
	}
	public void setInspectionNotes(String inspectionNotes) {
		this.inspectionNotes = inspectionNotes;
	}
	@Override
	public String toString() {
	    return "Rental [id=" + id + ", customer=" + customer + ", car=" + car
	            + ", totalAmount=" + totalAmount + ", status=" + status + "]";
	}
	
	
	
}
