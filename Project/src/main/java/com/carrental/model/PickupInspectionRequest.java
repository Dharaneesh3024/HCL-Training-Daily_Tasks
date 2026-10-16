package com.carrental.model;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;

public class PickupInspectionRequest {

    @NotNull(message = "Pickup odometer is required")
    @PositiveOrZero(message = "Pickup odometer cannot be negative")
    private Integer pickupOdometer;

    @NotNull(message = "Pickup fuel level is required")
    @DecimalMin(value = "0.0", message = "Fuel level cannot be below 0")
    @DecimalMax(value = "100.0", message = "Fuel level cannot exceed 100")
    private Double pickupFuelLevel;

    @Size(max = 255, message = "Pickup notes cannot exceed 255 characters")
    private String pickupNotes;

    public Integer getPickupOdometer() {
        return pickupOdometer;
    }

    public void setPickupOdometer(Integer pickupOdometer) {
        this.pickupOdometer = pickupOdometer;
    }

    public Double getPickupFuelLevel() {
        return pickupFuelLevel;
    }

    public void setPickupFuelLevel(Double pickupFuelLevel) {
        this.pickupFuelLevel = pickupFuelLevel;
    }

    public String getPickupNotes() {
        return pickupNotes;
    }

    public void setPickupNotes(String pickupNotes) {
        this.pickupNotes = pickupNotes;
    }
}

