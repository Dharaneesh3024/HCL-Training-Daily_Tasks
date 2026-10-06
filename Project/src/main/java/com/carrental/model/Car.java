package com.carrental.model;

public class Car {
	private int id;
	private String brand;
	private String model;
    private double pricePerDay;
    private boolean available;
    
	public Car(int id, String brand, String model, double pricePerDay, boolean available) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.pricePerDay = pricePerDay;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", model=" + model + ", pricePerDay=" + pricePerDay
				+ ", available=" + available + "]";
	}
    
	
}
